package com.api.bookstore.service;

import com.api.bookstore.entities.dtos.PublisherGet;
import com.api.bookstore.entities.dtos.PublisherPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {

    void create(PublisherPost publisherPost);

    Page<PublisherGet> getAll(Pageable pageable);

    PublisherGet getById(Long id);

    PublisherGet update(Long id, PublisherPost publisherPost);

    void deleteById(Long id);
}
