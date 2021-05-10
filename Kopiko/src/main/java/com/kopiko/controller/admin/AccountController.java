package com.kopiko.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kopiko.entity.Account;
import com.kopiko.service.impl.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping("/api/v1")
	@ResponseBody
	public String index(Model model) {
		model.addAttribute("account", accountService.findAll());
		return "list";
	}

	@GetMapping("admin/account/create")
	public String create(Model model) {
		model.addAttribute("account", new Account());
		return "admin/insert-account";
	}

	@GetMapping("/account/{accountId}/insert")
	public String edit(@PathVariable Long accountId, Model model) {
		model.addAttribute("account", accountService.findOne(accountId));
		return "form";
	}

	@PostMapping("/account/save")
	public String save(@Validated Account account, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "form";
		}
		accountService.save(account);
		redirect.addFlashAttribute("success", "Saved account successfully!");
		return "redirect:/account";
	}

	@GetMapping("/account/{accountId}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirect) {
		Account account = accountService.findOne(id);
		accountService.delete(account);
		redirect.addFlashAttribute("success", "Deleted account successfully!");
		return "redirect:/account";
	}

	@GetMapping("/account/search")
	public String search(@RequestParam("s") String s, Model model) {
		if (s.equals("")) {
			return "redirect:/account";
		}

		model.addAttribute("account", accountService.search(s));
		return "list";
	}
}
