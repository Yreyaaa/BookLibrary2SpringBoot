package ru.yreya.springcource.BookLibrary2SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yreya.springcource.BookLibrary2SpringBoot.models.Book;

import java.util.List;

/**
 * @author Neil Alishev
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {


    List<Book> findByTitleIgnoreCaseStartingWith(String search);


}
