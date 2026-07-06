package com.victot.gestao_ocorrencias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("hello world");
    }
}
