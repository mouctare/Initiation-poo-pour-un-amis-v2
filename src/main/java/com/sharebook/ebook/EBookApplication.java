package com.sharebook.ebook;

import com.sharebook.ebook.entity.Category;
import com.sharebook.ebook.entity.User;
import com.sharebook.ebook.repository.CategoryRepository;
import com.sharebook.ebook.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class EBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBookApplication.class, args);
	}
}