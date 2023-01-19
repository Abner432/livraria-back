package com.api.bookstore.service.impl;

import com.api.bookstore.entities.Publisher;
import com.api.bookstore.entities.dtos.PublisherGet;
import com.api.bookstore.entities.dtos.PublisherPost;
import com.api.bookstore.entities.mappers.PublisherMapper;
import com.api.bookstore.entities.validator.PublisherValidator;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.exceptions.IdFoundException;
import com.api.bookstore.repositories.PublisherRepository;
import com.api.bookstore.service.PublisherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherImpl implements PublisherService {
    @Autowired
    private final PublisherRepository publisherRepository;

    @Autowired
    private final PublisherMapper publisherMapper;

    @Autowired
    private final PublisherValidator publisherValidator;

    @Override
    public void create(PublisherPost publisherPost) {
        publisherValidator.validateForCreate(publisherPost);
        publisherRepository.save(publisherMapper.toPublisher(publisherPost));
    }

    @Override
    public Page<PublisherGet> getAll(Pageable pageable) {
        return publisherRepository.findAll(pageable).map(publisherMapper::toPublisherGet);
    }

    public PublisherGet getById(Long id) {
        return publisherRepository.findById(id).map(publisherMapper::toPublisherGet).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public PublisherGet update(Long id, PublisherPost publisherPost){
        Optional<Publisher> publisherById = publisherRepository.findById(id);
        Optional<Publisher> postPublisherName = publisherRepository.findByName(publisherPost.getName());

        if (!publisherById.equals(postPublisherName)){
            if (postPublisherName.isPresent()){
                publisherValidator.validateForCreate(publisherPost);
            }
        }

        if (publisherById.isEmpty()){
            throw new BusinessException("Editora não encontrada");
        }
        Publisher publisher = publisherMapper.toPublisher(publisherPost);
        publisher.setId(id);
        publisherRepository.save(publisher);

        return publisherMapper.toPublisherGet(publisher);
    }

    @Override
    public void deleteById(Long id) {
        publisherValidator.validateForDelete(id);
        publisherRepository.deleteById(id);
    }
}
