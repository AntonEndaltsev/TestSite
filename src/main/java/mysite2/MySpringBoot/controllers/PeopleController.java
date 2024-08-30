package mysite2.MySpringBoot.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.services.PeopleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Tag(name="Контроллер для вывода списка людей")
    @GetMapping()
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        // добавил доп. комментарий
        //System.out.println("!!!!");
        return "people/index";
    }

    @Tag(name="Контроллер для вывода информации по конкретному человеку")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBookByPersonId(id));
        return "people/show";
    }

    @Tag(name="Контроллер для вывода формы, добавляющей нового человека")
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @Tag(name="Контроллер для добавления нового человека в БД")
    @PostMapping()
    public String create(@ModelAttribute("person") Person person){
        peopleService.save(person);
        //System.out.println("---");
        return "redirect:/people";
    }

    @Tag(name="Контроллер для вывода формы, редактирующей поля существующего человека")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @Tag(name="Контроллер для обновления данных существующего человека")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        peopleService.update(id, person);

        return "redirect:/people";
    }

    @Tag(name="Контроллер для вывода удаления человека")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
