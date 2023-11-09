package ru.yreya.springcource.BookLibrary2SpringBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Book;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Person;
import ru.yreya.springcource.BookLibrary2SpringBoot.repositories.BookRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(Integer page, Integer books_per_page) {
        return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> findAll(Integer page, Integer books_per_page, Boolean sort_by_year) {

        if (sort_by_year != null && sort_by_year)
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
        return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();

    }

    public List<Book> findAll(Boolean sort_by_year) {

        if (sort_by_year != null && sort_by_year) return bookRepository.findAll(Sort.by("year"));
        return bookRepository.findAll();
    }


    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByTitleStartingWith(String search) {
        List<Book> foundBook = null;
        if (search != null) {
            foundBook = bookRepository.findByTitleIgnoreCaseStartingWith(search);
        }
        return foundBook;
    }


    public Optional<Person> getOwnerById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        Hibernate.initialize(book.get().getOwner());
        return Optional.ofNullable(book.get().getOwner());

    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }


    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.get().setOwner(null);
        book.get().setWasTakenOn(null);

        bookRepository.save(book.get());

    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Book> book = bookRepository.findById(id);
        book.get().setOwner(selectedPerson);
        book.get().setWasTakenOn(new Date());
        bookRepository.save(book.get());

    }


}
