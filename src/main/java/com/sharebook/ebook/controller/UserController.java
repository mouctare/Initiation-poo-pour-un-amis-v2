package com.sharebook.ebook.controller;

import com.sharebook.ebook.configuration.MyUserDetailService;
import com.sharebook.ebook.entity.User;
import com.sharebook.ebook.jwt.JwtUtils;
import com.sharebook.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
   private JwtUtils jwtUtils;
/**
    @PostMapping(value = "/users")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        List<User> users = userRepository.findByEmail(user.getEmail());
        if(!users.isEmpty()){
            return new ResponseEntity("User already existing",HttpStatus.BAD_REQUEST);
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setLastName(StringUtils.capitalize(user.getLastName()));
        user.setFirstName(StringUtils.capitalize(user.getFirstName()));

        userRepository.save(user);

        String token =  jwtUtils.generateToken(new MyUserDetailService.UserPrincipal(user));
        Cookie cookie = new Cookie("token", token);

        return new ResponseEntity(user, HttpStatus.CREATED);
        **/


        @PostMapping("/users")
        public ResponseEntity add(@Valid @RequestBody  User user, HttpServletResponse response) {

            List<User> users = userRepository.findByEmail(user.getEmail());
            if(!users.isEmpty()) {
                return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setLastName(StringUtils.capitalize(user.getLastName()));
            user.setFirstName(StringUtils.capitalize(user.getFirstName()));
            userRepository.save(user);
            String token = jwtUtils.generateToken(new MyUserDetailService.UserPrincipal(user));
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }

  /**  @GetMapping(value = "/isConnected")
    public ResponseEntity getUSerConnected() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsService) {
            return new ResponseEntity(((UserDetails) principal).getUsername(), HttpStatus.OK);
        }
        return new ResponseEntity("User is not connected", HttpStatus.FORBIDDEN);
    }
   **/

    @GetMapping(value = "/isConnected")
    public ResponseEntity getUSerConnected() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return new ResponseEntity(((UserDetails) principal).getUsername(), HttpStatus.OK);
        }
        return new ResponseEntity("User is not connected", HttpStatus.FORBIDDEN);
    }
}
