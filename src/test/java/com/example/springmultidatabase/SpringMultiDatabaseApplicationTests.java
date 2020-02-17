package com.example.springmultidatabase;

import com.example.springmultidatabase.model.mysql.Author;
import com.example.springmultidatabase.model.postgresql.Book;
import com.example.springmultidatabase.repository.mysql.AuthorRepository;
import com.example.springmultidatabase.repository.postgresql.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringMultiDatabaseApplicationTests {

    private static final Long BOOK_ID = 1l;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void init() {

        Author author = new Author();
        author.setFirstName("Jan");
        author.setLastname("Kowalski");
        authorRepository.save(author);

        Book book = new Book();
        book.setId(BOOK_ID);
        book.setAuthorId(author.getId());
        book.setName("Spring Boot");
        bookRepository.save(book);
    }

    @Test
    public void testShouldFindAuthor() {
        Book book = bookRepository.findById(BOOK_ID)
                                  .get();
        Assert.assertNotNull(book);

        Author author = authorRepository.findById(book.getAuthorId())
                                        .get();
        Assert.assertNotNull(author);

        log.info("Book {} was written {} {}", book.getName(), author.getFirstName(), author.getLastname());
    }

}
