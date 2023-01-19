package com.api.bookstore.entities.mappers;

import com.api.bookstore.entities.Rent;
import com.api.bookstore.entities.dtos.RentGet;
import com.api.bookstore.entities.dtos.RentPost;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component("RentMapper")
@RequiredArgsConstructor
public class RentMapper {
    private final ModelMapper mapper = new ModelMapper();

    public Rent toRent(RentPost rent){

        TypeMap<RentPost, Rent> typeMap = mapper.getTypeMap(RentPost.class, Rent.class);

        if (typeMap == null) {
            mapper.addMappings(new PropertyMap<RentPost, Rent>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        return mapper.map(rent, Rent.class);
    }

    public RentGet toRentGet(Rent rent){
        return mapper.map(rent, RentGet.class);
    }
}
