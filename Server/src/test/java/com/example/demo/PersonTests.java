package com.example.demo;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();

        List<Person> persons = List.of(
              new Person("Uku", "tuku", "Turu tn 1, Tartu"),
              new Person("Juku", "Juku", "Turu tn 1, Tartu")
        );
        personRepository.saveAll(persons);
    }

    @Test
    void shouldInsertIntoPersonsAndCheckIfSavedToDB() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
              .post("/api/person")
              .contentType("application/json")
              .content("{\"firstName\":\"Uku\",\"lastName\":\"Juku\",\"address\":\"Turu tn 5, Tartu\"}"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
              .andExpect(jsonPath("$.firstName").value("Uku"))
              .andExpect(jsonPath("$.lastName").value("Juku"))
              .andExpect(jsonPath("$.address").value("Turu tn 5, Tartu"));
    }

    @Test
    void shouldGetAllPersons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
              .get("/api/persons"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
              .andExpect(jsonPath("$.length()").isNotEmpty());
    }


    @Test
    void shouldFailIfInsertingNotCapitalizedLastName() throws Exception{

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                .post("/api/person")
                .contentType("application/json")
                .content("{\"firstName\":\"Uku\",\"lastName\":\"tuku\",\"address\":\"Turu tn 1, Tartu\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof IllegalArgumentException);
                    assertEquals("Last name must be capitalized.", result.getResolvedException().getMessage());
                });
        });
    }
}
