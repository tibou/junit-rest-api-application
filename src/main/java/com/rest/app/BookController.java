package com.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookService bookService;


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable(value = "id") Long id)  {
       return bookService.getById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody @Valid Book book) {
       return  bookService.save(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody @Valid Book book)  {
        return bookService.update(book);
    }

    // TODO: write delete endpoint using TDD method

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){

        bookService.delete(id);

        return  new ResponseEntity(HttpStatus.OK);
    }

}
