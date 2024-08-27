package mysite2.MySpringBoot.controllers;


import mysite2.MySpringBoot.models.Book;
import mysite2.MySpringBoot.models.Person;
import mysite2.MySpringBoot.services.BookService;
import mysite2.MySpringBoot.services.PeopleService;
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

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "books_per_page", required = false) Integer booksPerPage, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){
        if (page==null || booksPerPage==null)
            model.addAttribute("books", bookService.findAll(sortByYear));
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("owner", null);
        Person bookOwner = bookService.getBookOwner(id);

        if (bookOwner!=null) model.addAttribute("owner", bookOwner);
        else model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute(("book"), bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookService.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam(value = "owner", required = true) Integer ownerId) {
        Person selectedPerson = peopleService.findOne(ownerId);
        bookService.assign(id, selectedPerson);
        //System.out.println(selectedPerson.getName());
        return "redirect:/books/" +id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
