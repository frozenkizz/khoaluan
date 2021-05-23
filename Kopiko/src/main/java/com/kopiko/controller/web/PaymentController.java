/**
 * 
 */
package com.kopiko.controller.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kopiko.common.constant.Constants;
import com.kopiko.config.ConfigVNPay;
import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.entity.Account;
import com.kopiko.entity.OrderDetailEntity;
import com.kopiko.entity.OrderEntity;
import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.entity.ProductDetail;
import com.kopiko.model.Cart;
import com.kopiko.repository.IOrderRepository;
import com.kopiko.service.IAccountService;
import com.kopiko.service.IOrderDetailService;
import com.kopiko.service.IOrderService;
import com.kopiko.service.IPaymentMethodService;
import com.kopiko.service.IProductDetailService;
import com.kopiko.util.DateTimeUtil;
import com.kopiko.util.RandomUUID;


@Controller
public class PaymentController {
	@Autowired
	private IAccountService accountService;

	@Autowired
	private AccountCustomerConverter customerConverter;

	@Autowired
	private IPaymentMethodService paymentMethodService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private ProductShowListConverter productShowListConverter;

	@Autowired
	private IProductDetailService productDetailService;

	@Autowired
	private IOrderDetailService orderDetailService;
	
	@Autowired
	private IOrderRepository orderRepository;

	@PostMapping("/checkout/payment")
	public String payment(HttpSession session, @RequestParam Long paymentMethodId, HttpServletRequest request) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null || paymentMethodId == null) {
			cartItems = new HashMap<>();
			return "redirect:/checkout/cart/view"; // Nếu dữ liệu rỗng thì trở về trang checkout/cart
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if (username == null)
			return "redirect:/login";
		else {
			Account account = accountService.findByUsername(username);
			if (account != null && account.getStatus() == Constants.Account.ACTIVE_STATUS) { // Nếu xác thực thành công
																								// thì đưa dữ liệu từ
																								// cart vào database
				PaymentMethodEntity paymentMethod = paymentMethodService.findPaymentMethodById(paymentMethodId);
				if (paymentMethod == null) {
					System.out.println("payment method null!");
					return "redirect:/checkout/cart/view?message=error";
				}
				OrderEntity order = new OrderEntity();
				order.setAccount(account);
				order.setPaymentMethod(paymentMethod);
				order.setDeliveryInfo(account.getDeliveryInfo());
				order = orderService.save(order); // save vào database
				
				if (order == null) {
					System.out.println("Order null!");
					return "redirect:/checkout/cart/view?message=error";
				} else {
					for (Cart item : cartItems.values()) { // duyệt item trong cart
						OrderDetailEntity orderDetail = new OrderDetailEntity();
						orderDetail.setOrder(order); // set order cho order detail
						ProductDetail productDetail = productDetailService
								.findByProductDetailId(item.getProductDetailId()); // lấy thông tin productdetail
						// cho add vào db khi productDetail khác null và productdetail còn hàng
						if (productDetail != null && productDetail.getQuantity() >= item.getQuantity())
							orderDetail.setProductDetail(productDetail);
						else {
							System.out.println("Product detail ko hợp lệ!");
							return "redirect:/checkout/cart/view?message=error";
						}
						orderDetail.setQuantity(item.getQuantity());
						orderDetail.setSalePrice(item.getProduct().getSalePrice());
						
						// save vào database
						orderDetail = orderDetailService.save(orderDetail);
						productDetail.setQuantity(productDetail.getQuantity()-item.getQuantity()); // cập nhật số lượng mặt hàng
						productDetail = productDetailService.save(productDetail);
						if (orderDetail == null) {
							System.out.println("Order detail null!");
							return "redirect:/checkout/cart/view?message=error";
						}
					} // thêm data thành công
						// reset cart
					cartItems = new HashMap<>();
					session.setAttribute("myCartItems", cartItems);
					session.setAttribute("myCartNum", cartItems.size());
					session.setAttribute("myCartTotal", totalPrice(cartItems));

				}
				
				// Thanh toán online
				order = orderService.findByOrderId(order.getOrderId()); // lấy dữ liệu mới nhất từ db
				if(order.getPaymentMethod().getPaymentMethodId() == Constants.PaymentMethod.ONLINE_PAYMENT) {
					try {
						
						String paymentUrl = vnpay_ajax(request, order.getOrderId());
						if(paymentUrl != null) return "redirect:" + paymentUrl; // chuyen den trang thanh toan cua vnpay
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			} else
				System.out.println("Account không hợp lệ!");
		}

		return "redirect:/account/order";
	}
	
	
	public Long totalPrice(HashMap<Long, Cart> cartItems) {
        Long count = 0l;
        for (Map.Entry<Long, Cart> list : cartItems.entrySet()) {
            count += list.getValue().getProduct().getSalePrice().longValue() * list.getValue().getQuantity();
        }
        return count;
    }
	
	
	public String vnpay_ajax(HttpServletRequest request, Long orderId)
			throws UnsupportedEncodingException {
		
		if(orderId == null) return null;
				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		if(username == null) return null;

		Account account = accountService.findByUsername(username);
		if(account == null) return null;
		
		OrderEntity order = orderService.findByOrderIdAndAccountId(orderId, account.getAccountId());
		List<OrderDetailEntity> listOrderDetail = orderDetailService.findAllByOrderId(orderId);
		order.setListOrderDetail(listOrderDetail);
		if(order == null) return null;

		
		
		if(order.getAccount().getAccountId() != account.getAccountId()) {
			return null;
		}
		
		
		if(order.getPaymentMethod().getPaymentMethodId() != Constants.PaymentMethod.ONLINE_PAYMENT) {
			return null;
		}
		
		
		
		String vnp_Version = "2.0.0";
		String vnp_Command = "pay";
		String vnp_OrderInfo = "Thanh toan tien dat coc cho hoa don: #" + order.getOrderId() + ", thoi diem: " + DateTimeUtil.toStringDateTimeType(new Date());
		String orderType = "billpayment";
//		String vnp_TxnRef = ConfigVNPay.getRandomNumber(8);
		String vnp_TxnRef = order.getOrderId().toString();
		String vnp_IpAddr = ConfigVNPay.getIpAddress(request);
		String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;

		String vnp_TransactionNo = RandomUUID.getRandomID();
		String vnp_hashSecret = ConfigVNPay.vnp_HashSecret;

		long amount = order.getTotalPrice() * 100; // nhan 100 de bo phan thap phan(VNPay noi vay)
		System.out.println("Tong so tien thanh toan: " + order.getTotalPrice());
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = "vn";
		vnp_Params.put("vnp_Locale", locate);
		
		vnp_Params.put("vnp_ReturnUrl", ConfigVNPay.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Date dt = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(dt);
		String vnp_CreateDate = dateString;
		String vnp_TransDate = vnp_CreateDate;
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(fieldValue);
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = ConfigVNPay.Sha256(ConfigVNPay.vnp_HashSecret + hashData.toString());
		// System.out.println("HashData=" + hashData.toString());
		queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = ConfigVNPay.vnp_PayUrl + "?" + queryUrl;
		JsonObject job = new JsonObject();
		job.addProperty("code", "00");
		job.addProperty("message", "success");
		job.addProperty("data", paymentUrl);
		System.out.println("Payment Url: " + paymentUrl);
		Gson gson = new Gson();
		return paymentUrl;
	}
}
