package com.rest.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

   /* @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }*/


    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> {
                    return new RessourceNotFoundException("The Book with ID: " + id + " does not exist");
                }
        );
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        if (book == null || book.getBookId() == null) {
            throw new RessourceNotFoundException("Book or id must not be null");
        }


        bookRepository.findById(book.getBookId()).orElseThrow(
                () -> {
                    return new RessourceNotFoundException("The book with ID: " + book.getBookId() + " does not exist");
                }
        );

        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = getById(id);

        bookRepository.delete(book);
    }
}
