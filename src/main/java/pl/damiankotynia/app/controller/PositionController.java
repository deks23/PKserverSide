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
import pl.damiankotynia.app.exceptions.ValidationFailedException;
import pl.damiankotynia.app.service.UserService;

@RestController
@RequestMapping(value = "/position")
public class PositionController {

    @RequestMapping(value = "send", method = RequestMethod.POST, params = {"token", "x", "y"})
    public ResponseEntity sendPosition(@RequestParam String token, @RequestParam String x, @RequestParam String y){
        logger.info("Send position");
        Claims claims = null;
        try {
            claims = userService.parseJWT(token);
        } catch (ValidationFailedException e) {
            logger.warn("User unautorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return null;
    }

    @RequestMapping(value = "get", method = RequestMethod.POST, params = {"token", "user"})
    public ResponseEntity getPosition(@RequestParam String token, @RequestParam String user){
        logger.info("Get position");
        Claims claims = null;
        try {
            claims = userService.parseJWT(token);
        } catch (ValidationFailedException e) {
            logger.warn("User unautorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return null;
    }

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(PositionController.class);
}
