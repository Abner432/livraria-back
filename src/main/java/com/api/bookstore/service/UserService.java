package com.api.bookstore.service;

import com.api.bookstore.entities.dtos.UserGet;
import com.api.bookstore.entities.dtos.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void create(UserPost userPost);

    Page<UserGet> getAll(Pageable pageable);

    UserGet getById(Long id);

    UserGet update(Long id, UserPost userPost);

    void deleteById(Long id);
}
