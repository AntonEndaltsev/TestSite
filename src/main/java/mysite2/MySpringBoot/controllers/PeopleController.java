package mysite2.MySpringBoot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.models.PersonV1DTO;
import mysite2.MySpringBoot.models.PersonV2DTO;
import mysite2.MySpringBoot.services.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Tag(name="Вывод списка людей с разными версиями")
    @Operation(summary="v1 - выводит список людей (имя/возраст), v2 - выводит список людей (только имя), v3 + HttpHeader 'Option'='all' - выводит список людей(имя, возраст), v3 + Отсутствие HttpHeader 'Option' - выводит список людей (только имя)")
    @GetMapping("/{version}/people")
    public ResponseEntity<?>  index(@PathVariable("version") String version, @RequestHeader(value = "Option", required = false) String option){
        // comment
        if (version.equals("v1"))
            return new ResponseEntity<>(convertToDTO1(peopleService.findAll()), HttpStatus.OK);
        else if (version.equals("v2"))
                return new ResponseEntity<>(convertToDTO2(peopleService.findAll()), HttpStatus.OK);
        else if (version.equals("v3") && option.equals("all")) return new ResponseEntity<>(convertToDTO1(peopleService.findAll()), HttpStatus.OK);
        else if (version.equals("v3") && !option.equals("all")) return new ResponseEntity<>(convertToDTO2(peopleService.findAll()), HttpStatus.OK);

        //return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();


    }



    private List<?> convertToDTO1(List<Person> all) {
        List<PersonV1DTO> personV1DTO = new ArrayList<>();

        for (Person p:all){
            PersonV1DTO pV1DTO = new PersonV1DTO();
            pV1DTO.setName(p.getName());
            pV1DTO.setAge(p.getAge());
            personV1DTO.add(pV1DTO);
        }
        return personV1DTO;
    }

    private List<?> convertToDTO2(List<Person> all) {
        ArrayList<PersonV2DTO> personV2DTO = new ArrayList<>();

        for (Person p:all){
            PersonV2DTO pV2DTO = new PersonV2DTO();
            pV2DTO.setName(p.getName());
            personV2DTO.add(pV2DTO);
        }
        return personV2DTO;
    }

    @Tag(name="Контроллер для вывода информации по конкретному человеку")
    @GetMapping("/people/{id}")
    public ResponseEntity<?>  show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBookByPersonId(id));
        return new ResponseEntity<>(peopleService.findOne(id), HttpStatus.OK);
    }

    @Tag(name="Контроллер для вывода формы, добавляющей нового человека")
    @GetMapping("/new")
    public void newPerson(Model model){
        model.addAttribute("person", new Person());

        //return new Person();
    }

    @Tag(name="Контроллер для добавления нового человека в БД")
    @PostMapping()
    public void create(@ModelAttribute("person") Person person){
        peopleService.save(person);
        //System.out.println("---");
        //return peopleService.save(person);
    }

    @Tag(name="Контроллер для вывода формы, редактирующей поля существующего человека")
    @GetMapping("/{id}/edit")
    public void edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));
        //return peopleService.findOne(id);
    }

    @Tag(name="Контроллер для обновления данных существующего человека")
    @PatchMapping("/{id}")
    public void update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        peopleService.update(id, person);

        //return "redirect:/people";
    }

    @Tag(name="Контроллер для вывода удаления человека")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        peopleService.delete(id);
        //return "redirect:/people";
    }
}
