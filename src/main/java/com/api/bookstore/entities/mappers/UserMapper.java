package com.api.bookstore.entities.mappers;

import com.api.bookstore.entities.User;
import com.api.bookstore.entities.dtos.UserGet;
import com.api.bookstore.entities.dtos.UserPost;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("UserMapper")
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper mapper = new ModelMapper();

    public User toUser(UserPost rent){
        return mapper.map(rent, User.class);
    }

    public UserGet toUserGet(User rent){
        return mapper.map(rent, UserGet.class);
    }
}
