package com.guilherme.firststeps;

import com.guilherme.firststeps.model.Person;
import com.guilherme.firststeps.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Person getPersonById(@PathVariable(value = "id") Long id) throws Exception {
        return personServices.findById(id);
    }
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPersons() throws Exception {
        return personServices.findAll();
    }

    @PostMapping(path = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) throws Exception {
        return personServices.create(person);
    }

    @PutMapping(path = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person) throws Exception {
        return personServices.update(person);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePerson(@PathVariable(value = "id") Long id) throws Exception {
        personServices.delete(id);
    }
}
