package com.example.repositories;

import com.example.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepo extends JpaRepository<Sale, Long>, CrudRepository<Sale, Long> {
    Sale findAllById(long id);
}
