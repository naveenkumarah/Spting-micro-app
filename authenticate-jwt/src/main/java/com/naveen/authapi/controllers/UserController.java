package com.naveen.authapi.controllers;

import com.naveen.authapi.entities.User;
import com.naveen.authapi.repositories.UserRepository;
import com.naveen.authapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }


    @GetMapping
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
