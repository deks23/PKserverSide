package pl.damiankotynia.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.damiankotynia.app.model.User;
import pl.damiankotynia.app.repository.UserRepository;

import javax.transaction.Transactional;


@RestController
public class UserController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String qwe (){
        logger.info("Connection with database");
        User u1;

            u1 = userRepository.findById(1L).get();
            logger.info(u1.toString());


        return u1.toString();
    }

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







    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

}
