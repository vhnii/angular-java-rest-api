package com.example.demo.person;

import java.util.List;

import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.CustomResponse;
import com.example.demo.util.Message;



@RestController
@RequestMapping("/api")
// @CrossOrigin()
// @CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    private PersonService personService;


    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }




    @PostMapping(value = "/person", produces = "application/json")
    public ResponseEntity<CustomResponse<Person>> addPerson(@RequestBody Person person) {
        try {
            Person savedPerson = personService.addPerson(person);
            // CustomResponse<Person> response = new CustomResponse<>(savedPerson, HttpStatus.OK.value(), "Person created successfully.");
            Message message = new Message("Person created successfully.");
            CustomResponse<Person> response = new CustomResponse<>(savedPerson, HttpStatus.OK.value(), message);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Message message = new Message(e.getMessage());
            CustomResponse<Person> response = new CustomResponse<>(null, HttpStatus.BAD_REQUEST.value(), message);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Message message = new Message("Failed to create Person");
            CustomResponse<Person> response = new CustomResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
