/**
 * 
 */
package com.kopiko.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kopiko.common.constant.Constants;
import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.dto.AccountCustomerDTO;
import com.kopiko.entity.Account;
import com.kopiko.entity.OrderDetailEntity;
import com.kopiko.entity.OrderEntity;
import com.kopiko.entity.OrderStatusEntity;
import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.entity.ProductDetail;
import com.kopiko.model.Cart;
import com.kopiko.service.IAccountService;
import com.kopiko.service.IOrderDetailService;
import com.kopiko.service.IOrderService;
import com.kopiko.service.IPaymentMethodService;
import com.kopiko.service.IProductDetailService;
import com.kopiko.service.IProductService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:21:55 Thanh toán
 */

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

	@PostMapping("/checkout/payment")
	public String payment(HttpSession session, @RequestParam Long paymentMethodId) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
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
}
