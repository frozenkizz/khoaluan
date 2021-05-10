///**
// * 
// */
//package com.kopiko.security;
//
//import java.io.IOException;
//import java.security.Principal;
//import java.util.Collection;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.kopiko.common.constant.Constants;
//import com.kopiko.converter.AccountCustomerConverter;
//import com.kopiko.dto.AccountCustomerDTO;
//import com.kopiko.entity.Account;
//import com.kopiko.service.IAccountService;
//
///**
// * @author rianta9
// * @datecreated 29 thg 3, 2021 15:29:59
// */
//@Component
//public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private IAccountService accountService;
//    
//    @Autowired
//    private AccountCustomerConverter accountCustomerConverter;
//   
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//    	
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String username = auth.getName();
//		if(username != null) {
//			HttpSession session = request.getSession();
//			Account data = accountService.findByUsername(username);
//			if(data != null && data.getStatus() == Constants.Account.ACTIVE_STATUS) {
//				AccountCustomerDTO account = accountCustomerConverter.toDTO(data);
//				session.setAttribute("account", account);
//				String referer = request.getHeader("Referer");
//		        if(referer.contains("login")) {
//		        	if(account.getRole().equalsIgnoreCase("USER")) response.sendRedirect("/home"); // chuyển hướng theo role khi login thành công
//					else if(account.getRole().equalsIgnoreCase("ADMIN")) response.sendRedirect("/admin/home");
//					else response.sendRedirect("/banded");
//		        }
//		        else response.sendRedirect(referer);
//			}
//		}
//    }
//    
//    public CustomAuthenticationSuccessHandler() {
//        super();
//        setUseReferer(true);
//    }
//
//}
