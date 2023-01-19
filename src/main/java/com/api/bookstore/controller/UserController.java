package com.api.bookstore.controller;

import com.api.bookstore.entities.dtos.UserGet;
import com.api.bookstore.entities.dtos.UserPost;
import com.api.bookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserPost userPost) {
        userService.create(userPost);
    }

    @GetMapping
    public ResponseEntity<Page<UserGet>> getAllUsers(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserGet> getOneUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserGet> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserPost userPost) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userPost));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
    }
}
