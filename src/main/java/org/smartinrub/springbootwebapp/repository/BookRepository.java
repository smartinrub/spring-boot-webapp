package org.smartinrub.springbootwebapp.repository;

import org.smartinrub.springbootwebapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    
}
