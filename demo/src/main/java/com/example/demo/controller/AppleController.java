package com.example.demo.controller;

/*
This class is used to outline and create the connections that will be made in this API. 
*/

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.demo.model.Apple;
import com.example.demo.repository.AppleRepository;



@RestController
@RequestMapping("/api/apples")
@CrossOrigin(origins = "*")

public class AppleController {
    
    private final AppleRepository appleRepo;

    public AppleController(AppleRepository repo) {
        this.appleRepo = repo;
    }

    //GET Requests
    @GetMapping
    public List<Apple> getAllApples() {
        return appleRepo.findAll();
    }

    //POST Requests
    @PostMapping
    public Apple addApple(@RequestBody Apple apple) {
        
        return appleRepo.save(apple);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApple(@PathVariable Long id) {
        if(!appleRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appleRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apple> updateApple(
        @PathVariable Long id,
        @RequestBody Apple newApple 
    ) {
        return appleRepo.findById(id)
                .map(apple -> {
                    apple.setType(newApple.getType());
                    apple.setColor(newApple.getColor());
                    apple.setWeight(newApple.getWeight());

                    Apple saved = appleRepo.save(apple);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
}
