package com.example.demo;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonTests {

  @LocalServerPort
  private Integer port;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:16"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  PersonRepository personRepository;

  @BeforeEach
  void setUp() {
    baseURI = "http://localhost:" + port;
    personRepository.deleteAll();

    List<Person> persons = List.of(
      new Person("Uku", "tuku", "Turu tn 1, Tartu"),
      new Person("Juku", "Juku", "Turu tn 1, Tartu")
    );
    personRepository.saveAll(persons);
  }


  @Test
  void shouldInsertIntoPersonsAndCheckIfSavedToDB() {
    
   Person person =  new Person("Uku", "Juku", "Turu tn 1, Tartu");

   given()
   .contentType(ContentType.JSON)
   .log().all()
   .body(person)
   .when()
   .post("/api/person")
   .then()
   .statusCode(200)
   .body("data.firstName", equalTo(person.getFirstName()))
   .body("data.lastName", equalTo(person.getLastName()))
   .body("message.message", equalTo("Person created successfully."))
   .extract()
   .response();
  }


  @Test
  void shouldGetAllPersons() {
      given()
        .contentType(ContentType.JSON)
        .when() 
        .get("/api/persons")
        .then()
        .statusCode(200)
        .body(".", hasSize(2))
        .log();
  }



  @Test
  void shouldFailIfLastNameNotCapitalized() {
    
    Response response = given().contentType(ContentType.JSON).when().get("/api/persons");
    List<Person> responsePersons = response.jsonPath().getList("", Person.class);

    for (Person person : responsePersons) {
        assertTrue(Character.isUpperCase((person.getLastName()).charAt(0)), "Family name must be capitalized for " + " '" + person.getLastName() + "' ");
    }
  }
}
