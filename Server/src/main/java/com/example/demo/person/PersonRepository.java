package com.example.demo.person;

import org.springframework.data.jpa.repository.JpaRepository;

interface PersonRepository extends JpaRepository<Person, Long> {

}