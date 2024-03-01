package com.sharebook.ebook.repository;

import com.sharebook.ebook.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
