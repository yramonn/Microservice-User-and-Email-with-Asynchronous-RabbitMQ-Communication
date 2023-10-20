package com.ms.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.user.models.UserModel;
import com.ms.user.producer.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UseService {

     final UserRepository repository;
     final UserProducer producer;

    public UseService(UserRepository repository, UserProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }


    @Transactional
    public UserModel save(UserModel userModel) {
        userModel =  repository.save(userModel);
        if (userModel.getEmail()!= null) {
            try {
                producer.publishMessageEmail(userModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return userModel;
    }
}
