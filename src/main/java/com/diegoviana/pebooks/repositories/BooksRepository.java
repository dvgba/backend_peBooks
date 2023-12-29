package com.diegoviana.pebooks.repositories;

import com.diegoviana.pebooks.models.BooksModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository<id> extends JpaRepository<BooksModel, id> {

}
