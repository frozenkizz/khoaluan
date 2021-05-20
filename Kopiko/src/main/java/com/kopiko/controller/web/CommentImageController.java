package com.kopiko.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kopiko.entity.CommentImage;
import com.kopiko.service.impl.CommentImageService;
@Controller
public class CommentImageController {

	
	
	
	


@Autowired
private CommentImageService commentImageService;

@GetMapping("/commentImage")
public String index(Model model) {
	model.addAttribute("commentImage", commentImageService.findAll());
	return"list";
}

@GetMapping("/commentImage/create")
public String create(Model model) {
	model.addAttribute("commentImage",new CommentImage());
	return"form";
}
@GetMapping("/commentImage/{commentImageId}/insert")
public String edit(@PathVariable Long commentImageId, Model model) {
	model.addAttribute("commentImage",  commentImageService.findOne(commentImageId));
	return "form";
}

@PostMapping("/commentImage/save")
public String save(@Validated CommentImage commentImage, BindingResult result, RedirectAttributes redirect) {
	if (result.hasErrors()) {
		return "form";
	}
	commentImageService.save(commentImage);
	redirect.addFlashAttribute("success", "Saved commentImage successfully!");
	return "redirect:/commentImage";
}

@GetMapping("/commentImage/{commentImageId}/delete")
public String delete(@PathVariable Long id, RedirectAttributes redirect) {
	CommentImage commentImage = commentImageService.findOne(id); 
            commentImageService.delete(commentImage);
	redirect.addFlashAttribute("success", "Deleted commentImage successfully!");
	return "redirect:/commentImage";
}

@GetMapping("/commentImage/search")
public String search(@RequestParam("a") String a, Model model) {
	if (a.equals("")) {
		return "redirect:/commentImage";
	}

	model.addAttribute("commentImage", commentImageService.search(a));
	return "list";
}

}


