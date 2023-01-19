package com.api.bookstore.entities.mappers;

import com.api.bookstore.entities.Publisher;
import com.api.bookstore.entities.dtos.PublisherGet;
import com.api.bookstore.entities.dtos.PublisherPost;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("PublisherMapper")
@RequiredArgsConstructor
public class PublisherMapper {
    private final ModelMapper mapper = new ModelMapper();


    public Publisher toPublisher(PublisherPost publisher){
        return mapper.map(publisher, Publisher.class);
    }

    public PublisherGet toPublisherGet(Publisher publisher){
        return mapper.map(publisher, PublisherGet.class);
    }
}
