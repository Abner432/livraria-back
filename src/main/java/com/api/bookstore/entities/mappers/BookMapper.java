package com.api.bookstore.entities.mappers;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.dtos.BookGet;
import com.api.bookstore.entities.dtos.BookPost;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component("BookMapper")
@RequiredArgsConstructor
public class BookMapper {
    private final ModelMapper mapper = new ModelMapper();

    public Book toBooks(BookPost book){

        TypeMap<BookPost, Book> typeMap = mapper.getTypeMap(BookPost.class, Book.class);

        if (typeMap == null) {
            mapper.addMappings(new PropertyMap<BookPost, Book>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        return mapper.map(book, Book.class);
    }

    public BookGet toBooksGet(Book book){
        return mapper.map(book, BookGet.class);
    }
}
