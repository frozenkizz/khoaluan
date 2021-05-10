/**
 * 
 */
package com.kopiko.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.entity.Account;
import com.kopiko.entity.Product;
import com.kopiko.entity.ProductDetail;
import com.kopiko.model.Cart;
import com.kopiko.service.IAccountService;
import com.kopiko.service.IPaymentMethodService;
import com.kopiko.service.IProductDetailService;
import com.kopiko.service.IProductService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:21:36
 * Giỏ hàng
 */

@Controller
@RequestMapping("/checkout/cart")
public class CartController {
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ProductShowListConverter productShowListConverter;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IPaymentMethodService paymentMethodService;
	
	@Autowired
	private IProductDetailService productDetailService;
	
	@Autowired
	private AccountCustomerConverter accountCustomerConverter;
	
	@PostMapping("/add/{productId}")
	public String addCart(@PathVariable Long productId, @RequestParam(required = false) String size, @RequestParam(required = false) Integer quantity, HttpSession session, HttpServletRequest request) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        Product product = productService.findByProductId(productId);
        if(size == null) size = "Mặc định";
        if(quantity == null) quantity = 1;
        
        ProductDetail productDetail = productDetailService.findByProductIdAndSize(productId,size);
        String message = "";
        
        // kiểm tra database
        if (product != null && productDetail != null && product.getStatus() == 1 && productDetail.getQuantity() > quantity) {
        	System.out.println("Tìm thấy dữ liệu từ data!");
        	Cart item;
            if (cartItems.containsKey(productId)) item = cartItems.get(productId);
            else {
            	item = new Cart();
            	item.setQuantity(0);
            	item.setProduct(productShowListConverter.toDTO(product));
                item.setProductDetailId(productDetail.getProductDetailId());
                cartItems.put(productId, item);
            }
            
            item.setSize(size);            
            item.setQuantity(item.getQuantity() + quantity);
            
            
            
            session.setAttribute("myCartItems", cartItems);
            session.setAttribute("myCartNum", cartItems.size());
            session.setAttribute("myCartTotal", totalPrice(cartItems));   
        }
        
        else message = "?message=error";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer + message;
	}
	
	@PostMapping("/update/{productId}")
	public String updateCart(@PathVariable Long productId, @RequestParam Integer quantity, HttpSession session, HttpServletRequest request) {
		HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
		String message = "";
        if (cartItems == null) {
            cartItems = new HashMap<>();
            message = "?message=error";
        }
        else if(quantity != null && quantity > 0){
        	Product product = productService.findByProductId(productId); // kiểm tra database
            ProductDetail productDetail;
            if (product != null) { // tồn tại product
            	Cart item;
                if (cartItems.containsKey(productId)) { // kiểm tra tồn tại sản phẩm trong giỏ hàng
                	item = cartItems.get(productId); // lây sản phẩm ra
                	productDetail = productDetailService.findByProductDetailId(item.getProductDetailId()); // kiểm tra database
                	if(productDetail != null && productDetail.getQuantity() >= quantity) item.setQuantity(quantity); // sản phẩm còn hàng
                }
            }
        }
        else message = "?message=error";
        session.setAttribute("myCartItems", cartItems);
        session.setAttribute("myCartNum", cartItems.size());
        session.setAttribute("myCartTotal", totalPrice(cartItems));
        String referer = request.getHeader("Referer");
        return "redirect:" + referer + message;
	}
	
	public Long totalPrice(HashMap<Long, Cart> cartItems) {
		Long count = 0l;
        for (Map.Entry<Long, Cart> list : cartItems.entrySet()) {
            count += list.getValue().getProduct().getSalePrice().longValue() * list.getValue().getQuantity();
        }
        return count;
    }
	
	@GetMapping(value = "/view")
    public String viewCart(Model model, HttpSession session) {
        HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if(username != null) {
			Account account = accountService.findByUsername(username);
			model.addAttribute("account", accountCustomerConverter.toDTO(account));
			model.addAttribute("listPaymentMethod", paymentMethodService.findAll()); // nếu như đã đăng nhập thì mới add những thông tin này lên
		}
		
        session.setAttribute("myCartItems", cartItems);
        session.setAttribute("myCartNum", cartItems.size());
        session.setAttribute("myCartTotal", totalPrice(cartItems));
        return "web/cart";
    }

    @GetMapping(value = "remove/{productId}")
    public String viewRemove(ModelMap mm, HttpSession session, @PathVariable("productId") long productId, HttpServletRequest request) {
        HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("myCartItems");
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        if (cartItems.containsKey(productId)) {
            cartItems.remove(productId);
        }
        session.setAttribute("myCartItems", cartItems);
        session.setAttribute("myCartTotal", totalPrice(cartItems));
        session.setAttribute("myCartNum", cartItems.size());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
