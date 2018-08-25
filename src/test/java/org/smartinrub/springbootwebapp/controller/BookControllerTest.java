package org.smartinrub.springbootwebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.smartinrub.springbootwebapp.model.Book;
import org.smartinrub.springbootwebapp.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookServiceImpl bookServiceImpl;
    
    @Test
    public void findAllBooks_whenServiceReturnsData_thenReturnOkResponseAndBooks() throws Exception {
        Book book1 = new Book(1L, "test1", "test1", 2004, false, 1, 1);
        Book book2 = new Book(2L, "test2", "test2", 2004, false, 1, 1);
        Book book3 = new Book(3L, "test3", "test3", 2004, false, 1, 1);
        when(bookServiceImpl.findAllBooks()).thenReturn(Arrays.asList(book1, book2, book3));
        
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + "{\"id\":1,\"name\":\"test1\",\"author\":\"test1\",\"releaseDate\":2004,\"read\":false},"
                        + "{\"id\":2,\"name\":\"test2\",\"author\":\"test2\",\"releaseDate\":2004,\"read\":false},"
                        + "{\"id\":3,\"name\":\"test3\",\"author\":\"test3\",\"releaseDate\":2004,\"read\":false}]"));
    }

    @Test
    public void saveBook_givenBook_thenReturnCreatedResponse() throws Exception {
        Book book = new Book(1L, "test1", "test1", 2004, false, 1, 1);
        
        mockMvc.perform(post("/api/books")
                .content(asJsonString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void getBookById_givenBookId_whenBookExists_thenReturnOkResponseAndBook() throws Exception {
        Optional<Book> book = Optional.of(new Book(1L, "test1", "test1", 2004, false, 1, 1));
        when(bookServiceImpl.findBookById(book.get().getId())).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(content().json("{\"id\":1,\"name\":\"test1\",\"author\":\"test1\",\"releaseDate\":2004,\"read\":false}"));
    }

    @Test
    public void getBookById_givenBookId_whenBookDoesNotExistFound_thenReturnNotFoundResponse() throws Exception {
        when(bookServiceImpl.findBookById(5L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateBookById_givenBookId_whenBookExists_thenReturnOkResponse() throws Exception {
        Book updatedBook = new Book(1L, "test1", "test1", 2018, false, 1, 1);
        Optional<Book> book = Optional.of(new Book(1L, "test1", "test1", 2004, false, 1, 1));
        when(bookServiceImpl.findBookById(book.get().getId())).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                .content(asJsonString(updatedBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void updateBookById_givenBookId_whenBookDoesNotExistFound_thenReturnNotFoundResponse() throws Exception {
        Book updatedBook = new Book(1L, "test1", "test1", 2018, false, 1, 1);
        Optional<Book> book = Optional.empty();
        when(bookServiceImpl.findBookById(1L)).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                .content(asJsonString(updatedBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void deleteBookById_givenBookId_thenReturnOkResponse() throws Exception {
        Optional<Book> book = Optional.of(new Book(1L, "test1", "test1", 2004, false, 1, 1));
        when(bookServiceImpl.findBookById(book.get().getId())).thenReturn(book);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    public void deleteBookById_givenBookId_whenBookDoesNotExistFound_thenReturnNotFoundResponse() throws Exception {
        Optional<Book> book = Optional.empty();
        when(bookServiceImpl.findBookById(1L)).thenReturn(book);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNotFound()).andDo(print());
    }
    
    
    	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException(e);
	    }
	}  
    
}
