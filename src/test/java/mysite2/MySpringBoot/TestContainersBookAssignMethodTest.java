package mysite2.MySpringBoot;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import mysite2.MySpringBoot.models.Book;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.services.BookService;
import mysite2.MySpringBoot.services.PeopleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TestContainersBookAssignMethodTest {
        @LocalServerPort
        private Integer port;

//        private static String requestBody = "{\n" +
//            "  \"owner\": 88 \n}";

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

        @DynamicPropertySource
        static void configureProperties(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
        }

        @Autowired
        BookService bookService;
        //TodoRepository todoRepository;

        @Autowired
        PeopleService peopleService;



        @BeforeEach
        void setUp() {
            //todoRepository.deleteAll();
            RestAssured.baseURI = "http://localhost:" + port;
        }

        @Test
        @WithMockUser(username="Admin", password = "Admin")
        void shouldAssignCorrectly() {
            Person owner = new Person("Bob", 22, "booba@e1.ru", 1987);
            Person owner2 = new Person("Anton", 37, "mail@mail.ru", 1999);

            peopleService.save(owner);
            peopleService.save(owner2);

            Book book = new Book("Cold War2", "Stalin", 1941);


            bookService.save(book);

            Integer goodIdBook = bookService.findByTitle("Cold War2").getId();
            Integer goodIdPerson = peopleService.findByName("Anton").getId();
            //System.out.println(requestBody);

            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    //.body(requestBody)
                    .when()
                    //.get("/v1/people")
                    .param("owner", goodIdPerson)
                    .patch("/books/" + goodIdBook + "/assign")
                    .then()
                    //.statusCode(200)
                    .extract().response();

            Assertions.assertEquals(200, response.statusCode());
            //System.out.println(response.getHeaders().toString());

        }
}



//public void assign(@PathVariable("id") int id, @RequestParam(value = "owner", required = true) Integer ownerId) {
//    Person selectedPerson = peopleService.findOne(ownerId);
//    bookService.assign(id, selectedPerson);
//    //System.out.println(selectedPerson.getName());
//    // return "redirect:/books/" +id;
//}