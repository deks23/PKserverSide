package pl.damiankotynia.app.controller;

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
}
