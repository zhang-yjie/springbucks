package geektime.spring.springbucks.controller;

import geektime.spring.springbucks.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/")
    public Person getPerson(){
        Person person = (Person)applicationContext.getBean("person2");
        return person;
    }
}
