package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by gengf on 2019/10/3.
 */
@RestController
public class HelloController
{
    @Value("${test1}")
    private String name;


    @RequestMapping("/hello")
    public String hello(){

        return ("hello " + name);
    }

    @RequestMapping("/getDemo")
    public Demo getDemo(){
        Demo d = new Demo();
        d.setId("1");
        d.setName("name");
        return d;
    }


}
