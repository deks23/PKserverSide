package pl.damiankotynia.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String qwe (){
        return "qweqwe";
    }

    @RequestMapping(value = "/testBaza", method = RequestMethod.GET)
    String  qweBaza(){
        logger.info("Connection with database");
        return "qweqwe";
    }

    Logger logger = LoggerFactory.getLogger(TestController.class);

}
