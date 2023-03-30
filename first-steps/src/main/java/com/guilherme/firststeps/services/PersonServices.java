package com.guilherme.firststeps.services;

import com.guilherme.firststeps.exceptions.ResourceNotFoundException;
import com.guilherme.firststeps.model.Person;
import com.guilherme.firststeps.respositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {


    private Logger logger = LoggerFactory.getLogger(PersonServices.class);

    @Autowired
    PersonRepository personRepository;

    public Person findById(Long id){
        logger.info("Finding one person!");

        return personRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));
    }

    public List<Person> findAll(){

        return personRepository.findAll();
    }


    public Person create(Person person){

        logger.info("Creating Person");
        personRepository.save(person);
        return person;
    }
    public Person update(Person person){

        logger.info("Updating Person");
        var entity = personRepository.findById(person.getId())
                .orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }


    public void delete(Long id){

        var entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));
        personRepository.delete(entity);

        logger.info("Deleting Person");

    }
}
