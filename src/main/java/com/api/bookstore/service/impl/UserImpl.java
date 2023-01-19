package com.api.bookstore.service.impl;

import com.api.bookstore.entities.User;
import com.api.bookstore.entities.dtos.UserGet;
import com.api.bookstore.entities.dtos.UserPost;
import com.api.bookstore.entities.mappers.UserMapper;
import com.api.bookstore.entities.validator.UserValidator;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.exceptions.IdFoundException;
import com.api.bookstore.repositories.UserRepository;
import com.api.bookstore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    @Override
    public void create(UserPost userPost) {
        userValidator.validateForCreate(userPost);
        userRepository.save(userMapper.toUser(userPost));
    }

    @Override
    public Page<UserGet> getAll(Pageable pageable){
        return userRepository.findAll(pageable).map(userMapper::toUserGet);
    }

    @Override
    public UserGet getById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserGet).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public UserGet update(Long id, UserPost userPost){
        Optional<User> UserById = userRepository.findById(id);
        Optional<User> PostUserName = userRepository.findByName(userPost.getName());

        if (!PostUserName.equals(UserById)){
            if (UserById.isPresent()){
                userValidator.validateForCreate(userPost);
            }
        }

        if (UserById.isEmpty()){
            throw new BusinessException("Login not found");
        }

        User user = userMapper.toUser(userPost);
        user.setId(id);
        userRepository.save(user);

        return userMapper.toUserGet(user);
    }

    @Override
    public void deleteById(Long id) {
        userValidator.validateRelationship(id);
        userRepository.deleteById(id);
    }
}
