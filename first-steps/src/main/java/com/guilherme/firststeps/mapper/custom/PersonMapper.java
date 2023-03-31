package com.guilherme.firststeps.mapper.custom;


import com.guilherme.firststeps.data.vo.v2.PersonVOV2;
import com.guilherme.firststeps.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVOV2 convertEntityToVO(Person person){
        PersonVOV2 vov2 = new PersonVOV2();
        vov2.setId(person.getId());
        vov2.setAddress(person.getAddress());
        vov2.setBirthDay(new Date());
        vov2.setFirstName(person.getFirstName());
        vov2.setLastName(person.getLastName());
        vov2.setGender(person.getGender());
        return vov2;
    }
    public Person convertVOToEntity(PersonVOV2 personVO){
        Person entity = new Person();
        entity.setId(personVO.getId());
        entity.setAddress(personVO.getAddress());
//        entity.setBirthDay(new Date());
        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setGender(personVO.getGender());
        return entity;
    }
}

