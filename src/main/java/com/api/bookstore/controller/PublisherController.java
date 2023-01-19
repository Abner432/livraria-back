package com.api.bookstore.controller;

import com.api.bookstore.entities.dtos.PublisherGet;
import com.api.bookstore.entities.dtos.PublisherPost;
import com.api.bookstore.service.PublisherService;
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
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPublisher(@RequestBody @Valid PublisherPost publisherPost) {
        publisherService.create(publisherPost);
    }

    @GetMapping
    public ResponseEntity<Page<PublisherGet>> getAllPublishers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PublisherGet> getOnePublisher(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(publisherService.getById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PublisherGet> updatePublisher(@PathVariable(value = "id") Long id, @RequestBody @Valid PublisherPost publisherPost) {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.update(id, publisherPost));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable(value = "id") Long id) {
        publisherService.deleteById(id);
    }
}
