package com.sharebook.ebook.controller;

import com.sharebook.ebook.entity.Book;
import com.sharebook.ebook.entity.BookStatus;
import com.sharebook.ebook.entity.Borrow;
import com.sharebook.ebook.entity.User;
import com.sharebook.ebook.repository.BookRepository;
import com.sharebook.ebook.repository.BorrowRepository;
import com.sharebook.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class BorrowController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/borrows")
    public ResponseEntity getMyBorrows(Principal principal){
        Integer userConnectedId = BookController.getUserConnectedId(principal);
        // Afficher mes empreints
        List<Borrow> borrows = borrowRepository.findByBorrowerId(BookController.getUserConnectedId(principal));
        return new ResponseEntity(borrows, HttpStatus.OK);
    }

    // Faire un empreint
    @PostMapping("/borrows/{bookId}")
    public ResponseEntity createBorrow(@PathVariable("bookId") String bookId, Principal principal){
        Integer userConnected = BookController.getUserConnectedId(principal);
       Optional<User> borrower = userRepository.findById(userConnected);
      Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

      if(borrower.isPresent() && book.isPresent() && book.get().getStatus().equals(BookStatus.FREE)){
          Borrow borrow = new Borrow();
          Book bookEntity = book.get();
          borrow.setBook(bookEntity);
          borrow.setBorrower(borrower.get());
          borrow.setLender(book.get().getUser());
          borrow.setAskDate(LocalDate.now());
          borrowRepository.save(borrow);

          book.get().setStatus(BookStatus.BORROWED);
          bookRepository.save(bookEntity);
          return new ResponseEntity(HttpStatus.CREATED);
      }
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // Supprimer un empreint
    @DeleteMapping("/borrows/{bookId}")
    public ResponseEntity deleteBorrow(@PathVariable("borrowId") String borrowId){
        Optional<Borrow> borrow = borrowRepository.findById(Integer.valueOf(borrowId));
        if(borrow.isEmpty()){
           return  new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Borrow borrowEntity = borrow.get();
        borrowEntity.setCloseDate(LocalDate.now());
        borrowRepository.save(borrowEntity);

        Book book = borrowEntity.getBook();
        book.setStatus(BookStatus.FREE);
        bookRepository.save(book);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
