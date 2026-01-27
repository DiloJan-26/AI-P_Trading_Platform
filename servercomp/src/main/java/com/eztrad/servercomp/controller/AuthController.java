package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// step 11 - auth controller created set and get user credentials
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping ("/signup")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        // step 12 - go with postman check the post method with the url of http://localhost:5455/auth/signup to create user
        // step 13 - next add the jwt dependencies to pom.yml -> from mvn repository(jjwt-api, jjwt-impl, jjwt-jackson)
        // step 14 - at first(next to step-1) you made the spring security dependency be commented now you can un comment it
        // step 15 - so without spring security the http://localhost:5455 will showup the message that you wrote
        //           at HomeController but with it you can see the signup/login page and in terminal there is a line
        //           with generated password (user : user , password : prompted in terminal like
        //           Using generated security password: adbc4e24-43c6-46c9-904d-0b6f6fdbcf8b  )


    }
}
