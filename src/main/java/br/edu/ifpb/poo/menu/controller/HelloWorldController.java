package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping
    public String helloWorld() {
        return helloWorldService.helloWorld("Jéfter");
    }

    @PostMapping("/{id}")
    public String helloWorldPost(@PathVariable("id") String id, @RequestParam(value="filter", defaultValue = "nenhum") String filter, @RequestBody User body) {
        return "Hello World " + body.getName() + " - " + id + " - "+filter;
    }
}