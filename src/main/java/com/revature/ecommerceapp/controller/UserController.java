package com.revature.ecommerceapp.controller;



import com.revature.ecommerceapp.dto.CreateUserRequest;
import com.revature.ecommerceapp.models.UserEntity;
import com.revature.ecommerceapp.service.CustomUserDetailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CustomUserDetailService service;

    public UserController(CustomUserDetailService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody CreateUserRequest req) {
        return service.createUser(req);
    }
}

