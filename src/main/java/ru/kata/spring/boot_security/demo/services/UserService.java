package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void createUser(User user);
    User readUserById(Long id);
    List<User> readAllUsers();
    void updateUser(User user);
    void deleteUser(Long id);

}