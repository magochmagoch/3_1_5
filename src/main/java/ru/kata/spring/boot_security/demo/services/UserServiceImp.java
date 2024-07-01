package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createUser(User user, Collection<Role> roles) {
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User readUserById(int id) {
        Optional<User> user = userRepository.findById((long) id);
        return user.orElse(new User());
    }

    @Override
    public List<User> readAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updateUser(int id, User user) {
        try {
            User user0 = readUserById(id);
            user0.setUsername(user.getUsername());
            user0.setPassword(user.getPassword());
            user0.setEmail(user.getEmail());
            if (user.getRoles() == null) {
                user0.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
            }
            userRepository.save(user0);
        } catch (NullPointerException e) {
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById((long) id);
    }

}
