package mysite2.MySpringBoot;

import com.fasterxml.jackson.databind.ObjectMapper;
import mysite2.MySpringBoot.controllers.PeopleController;
import mysite2.MySpringBoot.repositories.PeopleRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

//юнит тестирование контроллера, репозиторий фэйковый, т.к. не затрагиваем другие слои

@WebMvcTest (PeopleController.class)
public class PeopleControllerTest1 {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PeopleRepository peopleRepository;

    @Test
    @WithMockUser(username="Admin", password = "Admin")
    public void givenId_whenGetExistingPerson_thenStatus200andPersonReturned() throws Exception {
//        Person person = new Person("Michael Jordan", 33);
//        person.setId(1);
//        Mockito.when(peopleRepository.findById(Mockito.any())).thenReturn(Optional.of(person));
//        System.out.println("!!!!");
//        mvc.perform(
//                get("/people/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.name").value("Michael Jordan"))
//                .andExpect(jsonPath("$.age").value("32"));
    }
}
