package com.kopiko.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kopiko.entity.Account;
import com.kopiko.entity.Comment;
import com.kopiko.entity.Product;
import com.kopiko.service.IAccountService;
import com.kopiko.service.ICommentService;
import com.kopiko.service.IProductService;

@Controller
public class CommentController {

	@Autowired
	private ICommentService commentService;
	
	@Autowired 
	private IProductService productService;
	
	@Autowired
	private IAccountService accountService;

	@GetMapping("/comment")
	public String index(Model model) {
		model.addAttribute("comment", commentService.findAll());
		return "list";
	}

	@PostMapping("/comment/{productId}")
	public String create(Model model, @PathVariable Long productId, @ModelAttribute Comment comment, HttpSession session) {
		Product product = productService.findByProductId(productId);
		if(product == null) return "redirect:/home";
		else {
			if(comment != null) {
				if(comment.getContent() != null && comment.getTitle() != null) {
					comment.setProduct(product);
					
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					String username = auth.getName();
					// Lấy username ra
					System.out.println("username get from authentication:" + username);
					if(username == null) return "redirect:/login";
					else {
						Account account = accountService.findByUsername(username);
						if(account != null && account.getStatus() == 1) {
							comment.setAccount(account);
							commentService.save(comment);
						}
						else System.out.println("Account không hợp lệ!");
					}
				}
				else System.out.println("Comment không có nội dung!");
			}
			else System.out.println("Comment null!");
		}
		return "redirect:/product/"+productId;
	}

	@GetMapping("/comment/{commentId}/edit")
	public String edit(@PathVariable Long commentId, Model model) {
		model.addAttribute("comment", commentService.findOne(commentId));
		return "form";
	}

	@PostMapping("/comment/save")
	public String save(@Validated Comment comment, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "form";
		}
		commentService.save(comment);
		redirect.addFlashAttribute("success", "Saved comment successfully!");
		return "redirect:/comment";
	}

	@GetMapping("/comment/{commentId}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirect) {
		Comment comment = commentService.findOne(id);
		commentService.delete(comment);
		redirect.addFlashAttribute("success", "Deleted comment successfully!");
		return "redirect:/comment";
	}

	@GetMapping("/comment/search")
	public String search(@RequestParam("s") String s, Model model) {
		if (s.equals("")) {
			return "redirect:/comment";
		}

		model.addAttribute("comment", commentService.search(s));
		return "list";
	}

}
