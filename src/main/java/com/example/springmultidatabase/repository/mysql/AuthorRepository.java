package com.example.springmultidatabase.repository.mysql;

import com.example.springmultidatabase.model.mysql.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
