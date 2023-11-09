package ru.yreya.springcource.BookLibrary2SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Person;

import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {


    Optional<Person> getPersonByFullName(String fullName);

}






