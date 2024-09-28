package com.naveen.authapi.controllers;

import com.naveen.authapi.entities.User;
import com.naveen.authapi.repositories.UserRepository;
import com.naveen.authapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
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
    private com.naveen.authapi.entities.User User;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/me")
    public ResponseEntity<String> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok((String)authentication.getPrincipal());
    }


    @GetMapping
    //@PreAuthorize("hasAuthority('User')")
    @PostFilter("hasRole('USER') and filterObject.email == authentication.principal")
    public List<User> allUsers() {
        return userService.allUsers();
    }
}
