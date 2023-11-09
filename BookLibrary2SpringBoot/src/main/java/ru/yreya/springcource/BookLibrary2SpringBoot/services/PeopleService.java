package ru.yreya.springcource.BookLibrary2SpringBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Book;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Person;
import ru.yreya.springcource.BookLibrary2SpringBoot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    private final long TEEN_DAY = 864000000;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.getPersonByFullName(fullName);

    }

    public List<Book> getBookByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBook());

            List<Book> books = person.get().getBook();
            books.forEach(book -> {
                book.setBookIsOverdue(new Date().getTime() - book.getWasTakenOn().getTime() > TEEN_DAY);
            });


            return books;
        } else {
            return Collections.emptyList();
        }
    }


    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
