package com.diegoviana.pebooks.controllers;

import com.diegoviana.pebooks.dtos.BooksRecordDto;
import com.diegoviana.pebooks.models.BooksModel;
import com.diegoviana.pebooks.repositories.BooksRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BooksController {

    @Autowired
    private BooksRepository<Long> booksRepository;

    //METHOD 01 - GET
    @GetMapping("/books")
    public ResponseEntity<List<BooksModel>> getAllBooks() {
        List<BooksModel> booksList = booksRepository.findAll();
        if(!booksList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(booksList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(booksList);
    }
    @GetMapping("/books/{id}")
    /*
    public ResponseEntity<BooksModel> getBookById(@PathVariable Long id) {
        Optional<BooksModel> book = booksRepository.findById(id);

        if(book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return  ResponseEntity.notFound().build();
        }
    }*/

    public ResponseEntity<BooksModel> getBookById(@PathVariable Long id) {
        return (ResponseEntity<BooksModel>) booksRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //METHOD 02 - POST
    @PostMapping("/books/batch")
    public ResponseEntity<List<BooksModel>> saveBooks(@RequestBody @Valid List<BooksRecordDto> booksRecordDtoList) {
        List<BooksModel> savedBooks = new ArrayList<>();

        for (BooksRecordDto dto : booksRecordDtoList) {
            BooksModel model = new BooksModel();
            BeanUtils.copyProperties(dto, model);
            savedBooks.add((BooksModel) booksRepository.save(model));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooks);
    }

    //METHOD 03 - PUT

    @PutMapping("/books/{id}")
    public ResponseEntity<BooksModel> updateBook(@PathVariable Long id, @RequestBody BooksRecordDto bookDetails) {
        Optional<BooksModel> bookOptional = booksRepository.findById(id);

        if (bookOptional.isPresent()) {
            BooksModel existingBook = bookOptional.get();
            BeanUtils.copyProperties(bookDetails, existingBook, "id"); // Ignore the id property
            booksRepository.save(existingBook);
            return ResponseEntity.ok(existingBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //METHOD 04 - DELETE
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return booksRepository.findById(id)
                .map(book -> {
                    booksRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
