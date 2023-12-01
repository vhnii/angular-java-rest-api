package com.example.demo.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
 //@CrossOrigin()
 @CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    private PersonService personService;


    @GetMapping("/persons")
    public List<Person> getAllPersons() {
            return personService.getAllPersons();
    }

    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        try {
            personService.addPerson(person);
            return ResponseEntity.ok(person);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Person.");
        }
    }
}
