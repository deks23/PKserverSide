package pl.damiankotynia.app.controller;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.damiankotynia.app.exceptions.*;
import pl.damiankotynia.app.model.User;
import pl.damiankotynia.app.repository.UserRepository;
import pl.damiankotynia.app.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static pl.damiankotynia.app.StringUtils.validateString;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    /**
     * metoda testowa, do wywalenia
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String qwe (){
        logger.info("Connection with database");
        User u1;

            u1 = userRepository.findById(1L).get();
            logger.info(u1.toString());


        return u1.toString();
    }

    /**
     * metoda testowa, do wywalenia
     * @return
     */
    @RequestMapping(value = "/testBaza", method = RequestMethod.GET)
    ResponseEntity qweBaza(){
        logger.info("Connection with database");
        User u1 = new User();
        u1.setName("damian");
        u1.setPasswordHash("ewqewqewq");
        try{
            userRepository.save(u1);
            logger.info(u1.toString());
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(u1.toString());
    }


    @RequestMapping(value = "/register", params = {"login", "password"}, method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam String login, @RequestParam String password){
        logger.info("User register " + login);
        try {
            login = validateString(login);
            password = validateString(password);
        } catch (StringPreparingException e) {
            logger.warn("Wrong login or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            userService.register(login, password);
        } catch (UserExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    /**
     *  logowanie uzytkownika
     *
     * @param login login
     * @param password haslo
     * @return zwraca token ktory nalezy przechowywac po stronie klienta i wysylac przy kazdym kolejnym requescie
     */

    @RequestMapping(value = "/login", params = {"login", "password"}, method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String login, @RequestParam String password){
        logger.info("User login " + login);
        try {
            login = validateString(login);
            password = validateString(password);
        } catch (StringPreparingException e) {
            logger.warn("Wrong login or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Map<String, String> responseMap = new HashMap<>();

        try {
            responseMap.put("token", userService.login(login, password));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (LoginFailedException e ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(responseMap);
    }

    /**
     * metoda testujaca dzialanie tokena, do wywalenia
     * @return
     */
    @RequestMapping(value = "/testJWT", params = {"token"}, method = RequestMethod.POST)
    public ResponseEntity testJWT(@RequestParam String token){
        logger.info("Testing token ");
        Claims claims;
        try {
            logger.info(userService.parseJWT(token).toString());
        } catch (ValidationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

}
