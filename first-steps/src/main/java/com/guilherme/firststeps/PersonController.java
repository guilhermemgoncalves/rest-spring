package com.guilherme.firststeps;

import com.guilherme.firststeps.data.vo.v1.PersonVOV1;

import com.guilherme.firststeps.data.vo.v2.PersonVOV2;
import com.guilherme.firststeps.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices personServices;
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV1 getPersonById(@PathVariable(value = "id") Long id) throws Exception {
        return personServices.findById(id);
    }
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVOV1> getPersons() throws Exception {
        return personServices.findAll();
    }

    @PostMapping(path = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV1 createV1(@RequestBody PersonVOV1 person) throws Exception {
        return personServices.create(person);
    }
    @PostMapping(path = "/v2",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) throws Exception {
        return personServices.createV2(person);
    }

    @PutMapping(path = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV1 update(@RequestBody PersonVOV1 person) throws Exception {
        return personServices.update(person);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "id") Long id) throws Exception {
        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
