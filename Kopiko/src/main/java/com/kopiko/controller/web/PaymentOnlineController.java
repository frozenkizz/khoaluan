/*
 * package com.kopiko.controller.web;
 * 
 * 
 * import java.io.UnsupportedEncodingException; import java.math.BigDecimal;
 * import java.net.URLEncoder; import java.nio.charset.StandardCharsets; import
 * java.text.DateFormat; import java.text.ParseException; import
 * java.text.SimpleDateFormat; import java.util.ArrayList; import
 * java.util.Collections; import java.util.Date; import java.util.Enumeration;
 * import java.util.HashMap; import java.util.Iterator; import java.util.List;
 * import java.util.Map; import java.util.TimeZone;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.google.gson.Gson; import com.google.gson.JsonObject; import
 * com.kopiko.config.ConfigVNPay; import com.kopiko.entity.OrderEntity; import
 * com.kopiko.entity.Invoice; import com.kopiko.repository.IOrderRepository;
 * import com.kopiko.repository.IOrderStatusRepository; import
 * com.kopiko.repository.InvoiceRepository; import com.kopiko.util.Constants;
 * import com.kopiko.util.MoneyHelper; import com.kopiko.util.RandomUUID;
 * 
 * 
 * @Controller public class PaymentController {
 * 
 * @Autowired private BookingRepository bookingRepository;
 * 
 * @Autowired private InvoiceRepository invoiceRepository;
 * 
 * @Autowired private BookingStatusRepository bookingStatusRepository;
 * 
 * 
 * @RequestMapping("/checkout/payment/result") public String vnpayReturn(Model
 * model, HttpServletRequest request, HttpSession session, HttpServletResponse
 * response) throws UnsupportedEncodingException, ParseException { Map fields =
 * new HashMap(); for (Enumeration params = request.getParameterNames();
 * params.hasMoreElements();) { String fieldName = (String)
 * params.nextElement(); String fieldValue = request.getParameter(fieldName); if
 * ((fieldValue != null) && (fieldValue.length() > 0)) { fields.put(fieldName,
 * fieldValue); } }
 * 
 * String vnp_SecureHash = request.getParameter("vnp_SecureHash"); if
 * (fields.containsKey("vnp_SecureHashType")) {
 * fields.remove("vnp_SecureHashType"); } if
 * (fields.containsKey("vnp_SecureHash")) { fields.remove("vnp_SecureHash"); }
 * 
 * String vnp_TxnRef = request.getParameter("vnp_TxnRef"); String vnp_Amount =
 * request.getParameter("vnp_Amount").substring(0,
 * request.getParameter("vnp_Amount").length() - 2); String vnp_OrderInfo =
 * request.getParameter("vnp_OrderInfo"); String vnp_ResponseCode =
 * request.getParameter("vnp_ResponseCode"); String vnp_TransactionNo =
 * request.getParameter("vnp_TransactionNo"); String vnp_BankCode =
 * request.getParameter("vnp_BankCode"); String vnp_PayDate =
 * request.getParameter("vnp_PayDate"); String signValue =
 * ConfigVNPay.hashAllFields(fields);
 * 
 * if(vnp_SecureHash == null || vnp_TxnRef == null || vnp_Amount == null ||
 * vnp_OrderInfo == null || vnp_OrderInfo == null || vnp_ResponseCode == null ||
 * vnp_TransactionNo == null || vnp_BankCode == null || vnp_PayDate == null) {
 * return "error/400"; }
 * 
 * model.addAttribute("vnp_TxnRef", vnp_TxnRef);
 * model.addAttribute("vnp_Amount",
 * MoneyHelper.toMoneyType(Long.valueOf(vnp_Amount)));
 * model.addAttribute("vnp_OrderInfo", vnp_OrderInfo);
 * model.addAttribute("vnp_ResponseCode", vnp_ResponseCode);
 * model.addAttribute("vnp_TransactionNo", vnp_TransactionNo);
 * model.addAttribute("vnp_BankCode", vnp_BankCode);
 * //model.addAttribute("vnp_PayDate", vnp_PayDate);
 * 
 * if (signValue.equals(vnp_SecureHash)) {
 * //model.addAttribute("vnp_SecureHash", vnp_SecureHash); if
 * ("00".equals(request.getParameter("vnp_ResponseCode"))) {
 * model.addAttribute("result",
 * "Giao dịch được thực hiện thành công. Cảm ơn quý khách đã sử dụng dịch vụ của LBrand!"
 * );
 * 
 * // Lưu giao dịch vào database Invoice invoice = new Invoice();
 * invoice.setBankCode(vnp_BankCode); invoice.setAmount(new
 * BigDecimal(vnp_Amount)); invoice.setTransactionNo(vnp_TransactionNo);
 * invoice.setOrderInfo(vnp_OrderInfo); SimpleDateFormat format = new
 * SimpleDateFormat("yyyyMMddHHmmss");
 * invoice.setPayDate(format.parse(vnp_PayDate));
 * invoice.setResponseCode(vnp_ResponseCode); invoice.setTxnRef(vnp_TxnRef);
 * 
 * Booking booking =
 * bookingRepository.findByBookingId(Long.valueOf(vnp_TxnRef)); if(booking !=
 * null) { invoice.setBooking(booking); invoiceRepository.saveAndFlush(invoice);
 * if(booking.getBookingStatus().getBookingStatusId() ==
 * Constants.BookingStatus.WAIT_FOR_CONFIRM_AND_UNPAID_DEPOSIT) {
 * booking.setBookingStatus(bookingStatusRepository.getOne(Constants.
 * BookingStatus.WAIT_FOR_CONFIRM_AND_PAID_DEPOSIT)); } else
 * if(booking.getBookingStatus().getBookingStatusId() ==
 * Constants.BookingStatus.CONFIRMED_AND_UNPAID_DEPOSIT) {
 * booking.setBookingStatus(bookingStatusRepository.getOne(Constants.
 * BookingStatus.CONFIRMED_AND_PAID_DEPOSIT)); }
 * bookingRepository.saveAndFlush(booking); }
 * 
 * } else { model.addAttribute("result", "Giao dịch không thành công!"); }
 * 
 * } else { model.addAttribute("result", "Chu kỳ giao dịch không hợp lệ!"); }
 * return "web/payment-result"; } }
 */