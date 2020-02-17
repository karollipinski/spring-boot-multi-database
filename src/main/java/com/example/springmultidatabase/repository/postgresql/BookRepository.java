package com.example.springmultidatabase.repository.postgresql;

import com.example.springmultidatabase.model.postgresql.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
