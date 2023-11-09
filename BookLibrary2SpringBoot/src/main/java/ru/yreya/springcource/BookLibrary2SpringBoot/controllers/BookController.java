package ru.yreya.springcource.BookLibrary2SpringBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Book;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Person;
import ru.yreya.springcource.BookLibrary2SpringBoot.services.BookService;
import ru.yreya.springcource.BookLibrary2SpringBoot.services.PeopleService;

import java.util.List;
import java.util.Optional;


/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/book")
public class BookController {

    //private final BookDAO bookDAO;

    private final BookService bookService;
    private final PeopleService peopleService;


    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;

        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "books_per_page", required = false) Integer books_per_page, @RequestParam(value = "sort_by_year", required = false) Boolean sort_by_year) {
        System.out.println(page + " " + books_per_page);
        if (page != null && books_per_page != null) {

            model.addAttribute("book", bookService.findAll(page, books_per_page, sort_by_year));

        } else model.addAttribute("book", bookService.findAll(sort_by_year));

        return "book/index";
    }


    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "search_query", required = false) String search_query) {

        List<Book> searchBook = bookService.findByTitleStartingWith(search_query);

        model.addAttribute("search_book", searchBook);
        return "book/search";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = bookService.getOwnerById(id);

        if (bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", peopleService.findAll());

        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book Book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "book/new";

        bookService.save(Book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "book/edit";

        Optional<Person> owner = (bookService.getOwnerById(id));
        if (owner.isPresent()) book.setOwner(owner.get());
        bookService.update(id, book);
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/book/" + id;
    }
}
