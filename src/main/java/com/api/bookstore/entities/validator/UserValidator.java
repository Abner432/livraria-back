package com.api.bookstore.entities.validator;

import com.api.bookstore.entities.Rent;
import com.api.bookstore.entities.dtos.UserPost;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    public void validateForCreate(@NotNull UserPost userPost){
        validateName(userPost.getName());
    }

    private void validateName(String name) {
        userRepository.findByName(name).ifPresent(userModel -> {
            throw new BusinessException("Usuario já registrado");
        });
    }

    public void validateRelationship(Long id) {
        List<Rent> rent = userRepository.findById(id).orElseThrow().getRents();

        if (!rent.isEmpty()) {
            throw new BusinessException("Este leitor tem um aluguel em andamento");
        }
    }
}
