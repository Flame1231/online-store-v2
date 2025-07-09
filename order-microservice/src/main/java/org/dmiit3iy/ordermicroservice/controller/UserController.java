package org.dmiit3iy.ordermicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.dmiit3iy.ordermicroservice.model.User;
import org.dmiit3iy.ordermicroservice.model.dto.UserDTO;
import org.dmiit3iy.ordermicroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "User Controller", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получить пользователя по ID")
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        User newUser = userService.add(user);
        UserDTO userDTO = new UserDTO(newUser);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findByID(id);
        UserDTO userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
