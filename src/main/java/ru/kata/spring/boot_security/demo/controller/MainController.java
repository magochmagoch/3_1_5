package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String adminInfo(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "user";
    }
/*
    @GetMapping("/admin/create")
    public String createForm() {

    }

    @PostMapping("/admin/createauser")
    public String create() {

    }

    @GetMapping("/admin/update")
    public String updateForm() {

    }

    @PostMapping("/admin/updateauser")
    public String update() {

    }

    @GetMapping("/admin/delete")
    public String delete() {

    }
*/
}