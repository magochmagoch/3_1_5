package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String readUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/admin")
    public String readAllUsers(Model model) {
        model.addAttribute("users", userService.readAllUsers());
        return "admin_page";
    }

    @GetMapping("/admin/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "create";
    }

    @PostMapping("/admin/createauser")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update")
    public String updateForm(Model model,
                             @RequestParam("id") Long id) {
        model.addAttribute(userService.readUserById(id));
        return "update";
    }

    @PostMapping("/admin/updateauser")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam("role") String selectedRole,
                         @RequestParam("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        if (selectedRole.equals("ROLE_USER")) {
            user.setRoles(roleService.findByName("ROLE_USER"));
        } else if (selectedRole.equals("ROLE_ADMIN")) {
            user.setRoles(roleService.findAll());
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteForm(Model model,
                             @RequestParam("id") Long id) {
        model.addAttribute(userService.readUserById(id));
        return "delete";
    }

    @PostMapping("/admin/deleteauser")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}