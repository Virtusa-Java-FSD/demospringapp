package com.revature.ecommerceapp.service;



import com.revature.ecommerceapp.dto.CreateUserRequest;
import com.revature.ecommerceapp.models.UserEntity;
import com.revature.ecommerceapp.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public CustomUserDetailService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())//authorization -admin, user, seller
                .build();
    }

    public UserEntity createUser(CreateUserRequest req) {

        if (repo.existsByUsername(req.username())) {
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        user.setRole(req.role());// Encrypt here

        return repo.save(user);
    }
}
