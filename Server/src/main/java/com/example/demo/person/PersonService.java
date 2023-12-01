package com.example.demo.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Transactional
    public Person addPerson(Person person) {
        if (person.getFirstName() == null || person.getLastName() == null || person.getAddress() == null) {
            throw new IllegalArgumentException("First name, last name and address are required.");
        }

        if (!Character.isUpperCase(person.getLastName().charAt(0))) {
            throw new IllegalArgumentException("Last name must be capitalized.");
        }

        return personRepository.save(person);
    }
}

