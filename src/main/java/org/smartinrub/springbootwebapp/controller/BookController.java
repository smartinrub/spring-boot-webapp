package org.smartinrub.springbootwebapp.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;

import org.smartinrub.springbootwebapp.model.Book;
import org.smartinrub.springbootwebapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("*")
public class BookController {
    
    private final BookService bookService;
    
    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }


    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity findBookById(@PathVariable("id") Long id) {
        Optional<Book> book = bookService.findBookById(id);
        if (!book.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateBookById(@PathVariable("id") Long id, @Valid @RequestBody Book book) {
        Optional<Book> existingBook = bookService.findBookById(id);
        if (!existingBook.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        bookService.saveBook(book);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteBookById(@PathVariable("id") Long id) {
        Optional<Book> existingBook = bookService.findBookById(id);
        if (!existingBook.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBookById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
