package com.api.bookstore.controller;

import com.api.bookstore.entities.dtos.RentGet;
import com.api.bookstore.entities.dtos.RentPost;
import com.api.bookstore.service.RentService;
import io.swagger.annotations.Api;
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
@Api(value = "API REST BOOKS")
@RequestMapping("/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRent(@RequestBody @Valid RentPost rentPost) {
        rentService.create(rentPost);
    }

    @GetMapping
    public ResponseEntity<Page<RentGet>> getAllRents(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RentGet> getONERent(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(rentService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RentGet> updateRent(@PathVariable(value = "id") Long id, @RequestBody @Valid RentPost rentPost) {
        return ResponseEntity.status(HttpStatus.OK).body(rentService.update(id, rentPost));
    }

    @PutMapping(value = "Devolution/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void devolution(@PathVariable(value = "id") Long id) {
        rentService.devolution(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRent(@PathVariable(value = "id") Long id) {
        rentService.deleteById(id);
    }
}
