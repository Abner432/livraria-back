package com.api.bookstore.service;

import com.api.bookstore.entities.dtos.RentGet;
import com.api.bookstore.entities.dtos.RentPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentService {

    void create(RentPost rentPost);

    Page<RentGet> getAll(Pageable pageable);

    RentGet getById(Long id);

    RentGet update(Long id, RentPost rentPost);

    void devolution(Long id);

    void deleteById(Long id);
}
