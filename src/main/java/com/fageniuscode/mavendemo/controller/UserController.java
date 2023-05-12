package com.fageniuscode.mavendemo.controller;

import com.fageniuscode.mavendemo.dto.UserDTO;
import com.fageniuscode.mavendemo.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ibrahima
 *
 */
@RestController
@RequestMapping("/users")
@ComponentScan
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAppUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getAppUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    //@IsAdmin
    public UserDTO createAppUser(@Valid @RequestBody UserDTO appUser) {
        return userService.createUser(appUser);
    }

    @PutMapping("/{id}")
    //@IsAdmin
    public UserDTO updateAppUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO appUser) {
        return userService.updateUser(id, appUser);
    }

    @DeleteMapping("/{id}")
    public void deleteAppUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }

}
