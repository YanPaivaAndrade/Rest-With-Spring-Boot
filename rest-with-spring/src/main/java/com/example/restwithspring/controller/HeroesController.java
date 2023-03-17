package com.example.restwithspring.controller;

import com.example.restwithspring.dtos.HeroDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Hero")
public class HeroesController {
    @PostMapping
    public ResponseEntity<Object> saveHero(@RequestBody HeroDto heroDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(new Object());
    }

    @GetMapping
    public ResponseEntity<List<HeroDto>> getAllHeroes(){
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<HeroDto>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getHero(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(new HeroDto());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHero(@PathVariable(value = "id") int id){
        return ResponseEntity.status(HttpStatus.OK).body("Heroi deletado");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHero(@PathVariable(value = "id") int id,
                                             @RequestBody HeroDto heroDto){
        return ResponseEntity.status(HttpStatus.OK).body(new HeroDto());

    }
}
