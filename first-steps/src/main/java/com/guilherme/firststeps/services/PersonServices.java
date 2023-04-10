package com.guilherme.firststeps.services;

import com.guilherme.firststeps.PersonController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {


    private final Logger logger = LoggerFactory.getLogger(PersonServices.class);

    @Autowired
    PersonRepository personRepository;

    public PersonVOV1 findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("NO Records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVOV1> findAll() {
        var persons = DozerMapper.parseListObject(personRepository.findAll(), PersonVOV1.class);

        persons.stream().forEach(
                p -> p.add((linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel()))
        );
        return persons;
    }


    public PersonVOV1 create(PersonVOV1 person) {
        logger.info("Creating Person");
        var entity = DozerMapper.parseObject(person, Person.class);
        personRepository.save(entity);
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).getPersonById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV1 update(PersonVOV1 person) {
        logger.info("Updating Person");
        var entity = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("NO Records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).getPersonById(vo.getKey())).withSelfRel());
        return vo;
    }


    public void delete(Long id) {
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NO Records found for this ID!"));
        personRepository.delete(entity);
        logger.info("Deleting Person");
    }
}
