package ru.yreya.springcource.BookLibrary2SpringBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;


@Entity
@Table(name = "book", schema = "public", catalog = "LibraryWebAppDatabase")
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "author", nullable = false, length = 100)
    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
    private String author;
    @Basic
    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    @Column(name = "year", nullable = false)
    private int year;

    public Date getWasTakenOn() {
        return wasTakenOn;
    }

    public Boolean getIsBookIsOverdue() {
        return bookIsOverdue;
    }

    public void setBookIsOverdue(boolean bookIsOverdue) {
        this.bookIsOverdue = bookIsOverdue;
    }

    @Transient
    boolean bookIsOverdue;


    public void setWasTakenOn(Date wasTakenOn) {
        this.wasTakenOn = wasTakenOn;
    }

    @Column(name = "was_taken_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wasTakenOn;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
