package com.guilherme.firststeps.services;

import com.guilherme.firststeps.data.vo.v1.PersonVOV1;
import com.guilherme.firststeps.exceptions.ResourceNotFoundException;

import com.guilherme.firststeps.mapper.DozerMapper;
import com.guilherme.firststeps.model.Person;
import com.guilherme.firststeps.respositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServices {


    private final Logger logger = LoggerFactory.getLogger(PersonServices.class);

    @Autowired
    PersonRepository personRepository;

    public PersonVOV1 findById(Long id){
        logger.info("Finding one person!");

        var entity = personRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));

        return DozerMapper.parseObject(entity, PersonVOV1.class);
    }

    public List<PersonVOV1> findAll(){

        return DozerMapper.parseListObject(personRepository.findAll(), PersonVOV1.class);
    }


    public PersonVOV1 create(PersonVOV1 person){
        logger.info("Creating Person");
        var entity = DozerMapper.parseObject(person, Person.class);
        personRepository.save(entity);
        return DozerMapper.parseObject(personRepository.save(entity), PersonVOV1.class);
    }

    public PersonVOV1 update(PersonVOV1 person){

        logger.info("Updating Person");
        var entity = personRepository.findById(person.getId())
                .orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return DozerMapper.parseObject(personRepository.save(entity), PersonVOV1.class);
    }


    public void delete(Long id){

        var entity = personRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("NO Records found for this ID!"));
        personRepository.delete(entity);
        logger.info("Deleting Person");

    }
}
