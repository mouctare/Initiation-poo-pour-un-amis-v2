package com.sharebook.ebook.repository;

import com.sharebook.ebook.entity.Borrow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BorrowRepository extends CrudRepository<Borrow, Integer> {

    List<Borrow> findByBorrowerId(Integer borrowerId);
    List<Borrow> findByBookId(Integer borrowerId);
}
