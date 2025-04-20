package com.example.lab4.controllers;

import com.example.lab4.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok(userService.getUserData());
    }
}
