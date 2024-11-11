package com.dif76oq.userMicroservice.controller;
import com.dif76oq.userMicroservice.model.User;
import com.dif76oq.userMicroservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAllUsers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        Optional<User> userOptional = service.findById(id);

        return userOptional
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public User findByPhone(@PathVariable String phone) {
        return service.findByPhone(phone);
    }

    //TODO сделать чтобы можно было редачить только определенную часть User!!!!!
    //TODO возможно сделать dto phone/datebirth
    @PutMapping("update_user")
    public String updateUser(@RequestBody User user, @RequestHeader("Authorization") String authorizationHeader) {
        return service.updateUser(user, getToken(authorizationHeader));
    }

    @DeleteMapping("delete_user/{id}")
    public String deleteUser(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader) {
        return service.deleteUser(id, getToken(authorizationHeader));
    }

    private String getToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header must be provided with Bearer token");
        }
        return header.replace("Bearer ", "").trim();
    }

}
