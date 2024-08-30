package mysite2.MySpringBoot.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import mysite2.MySpringBoot.models.Book;
import mysite2.MySpringBoot.models.MyUserDetails;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.services.BookService;
import mysite2.MySpringBoot.services.PeopleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;


    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @Tag(name="Контроллер для вывода списка книг")
    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "books_per_page", required = false) Integer booksPerPage, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails personDetails = (MyUserDetails) authentication.getPrincipal();
            System.out.println(personDetails.getUser());
            model.addAttribute("loginuser", personDetails.getUser());
        }
        else {
            model.addAttribute("loginuser", null);
            System.out.println("----");
        }

        if (page==null || booksPerPage==null)
            model.addAttribute("books", bookService.findAll(sortByYear));
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));
        return "books/index";
    }



    @Tag(name="Контроллер для вывода инфы по конкретной книге + показывает кто взял эту книгу с возможность удалить владельца + если книга ничья, то выводится список всех людей и возможность забронировать книгу")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("owner", null);
        Person bookOwner = bookService.getBookOwner(id);

        if (bookOwner!=null) model.addAttribute("owner", bookOwner);
        else model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @Tag(name="Контроллер для вывода формы, добавляющую новую книгу")
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @Tag(name="Контроллер для добавления новой книги")
    @PostMapping()
    public String create(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:/books";
    }

    @Tag(name="Контроллер для вывода формы, редактирующую выбранную книгу")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute(("book"), bookService.findOne(id));
        return "books/edit";
    }

    @Tag(name="Контроллер для обновления данных выбранной книги")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookService.update(id,book);
        return "redirect:/books";
    }

    @Tag(name="Контроллер для удаления выбранной книги")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @Tag(name="Контроллер для удаления владельца выбранной книги")
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/"+id;
    }

    @Tag(name="Контроллер для установки владельца выбранной книги")
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam(value = "owner", required = true) Integer ownerId) {
        Person selectedPerson = peopleService.findOne(ownerId);
        bookService.assign(id, selectedPerson);
        //System.out.println(selectedPerson.getName());
        return "redirect:/books/" +id;
    }

    @Tag(name="Контроллер для вывода формы поиска книг по названию с возможностью пагинации и сортировки по году (параметры page, books_per_page, sortbyyear)")
    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @Tag(name="Контроллер для вывода результатов поиска с учетом пагинации и сортировки по году выпуска книги")
    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
