package mysite2.MySpringBoot;

import com.fasterxml.jackson.databind.ObjectMapper;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.models.PersonV1DTO;
import mysite2.MySpringBoot.models.PersonV2DTO;
import mysite2.MySpringBoot.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// интеграционный тест поднимающий весь контекст, все слои
@SpringBootTest
@AutoConfigureMockMvc
public class MySpringBootApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username="Admin", password = "Admin")
    void findAllShouldWorkCorrectly() throws Exception {
        List<PersonV1DTO> goodListPersonV1DTO = new ArrayList<>();
        goodListPersonV1DTO = convertToDTO1(peopleService.findAll());
        String jsonGood1 =  objectMapper.writeValueAsString(goodListPersonV1DTO);

        List<PersonV2DTO> goodListPersonV2DTO = new ArrayList<>();
        goodListPersonV2DTO = convertToDTO2(peopleService.findAll());
        String jsonGood2 =  objectMapper.writeValueAsString(goodListPersonV2DTO);

        MvcResult result = this.mvc.perform(get("/v1/people"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(jsonGood1, jsonResult);

        result = this.mvc.perform(get("/v2/people"))
                .andExpect(status().isOk())
                .andReturn();

        jsonResult = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(jsonGood2, jsonResult);


    }


    private List<PersonV1DTO> convertToDTO1(List<Person> all) {
        List<PersonV1DTO> personV1DTO = new ArrayList<>();

        for (Person p : all) {
            PersonV1DTO pV1DTO = new PersonV1DTO();
            pV1DTO.setName(p.getName());
            pV1DTO.setAge(p.getAge());
            personV1DTO.add(pV1DTO);
        }
        return personV1DTO;
    }

    private List<PersonV2DTO> convertToDTO2(List<Person> all) {
        List<PersonV2DTO> personV2DTO = new ArrayList<>();

        for (Person p : all) {
            PersonV2DTO pV2DTO = new PersonV2DTO();
            pV2DTO.setName(p.getName());
            personV2DTO.add(pV2DTO);
        }
        return personV2DTO;
    }

}