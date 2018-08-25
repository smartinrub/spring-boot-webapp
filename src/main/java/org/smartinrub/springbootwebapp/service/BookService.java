package org.smartinrub.springbootwebapp.service;

import org.smartinrub.springbootwebapp.model.Book;

import java.util.List;
import java.util.Optional;


public interface BookService {
    
    List<Book> findAllBooks();
    
    void saveBook(Book book);

    Optional<Book> findBookById(Long id);

    void deleteBookById(Long id);
}
