package com.example.restwithspring.controller;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.model.Hero;
import com.example.restwithspring.services.HeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Hero")
public class HeroesController {
    @Autowired
    private HeroService heroService;
    @PostMapping
    public ResponseEntity<Object> saveHero(@RequestBody @Valid HeroDto heroDto){
        var hasHeroInDataBase = heroService.hasHero(heroDto.getName());
        if(hasHeroInDataBase){
            return  ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflito: Existe um heroi cadastrado com este nome");
        }
        var result = heroService.save(heroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<HeroDto>> getAllHeroes(){
        var result = heroService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
                                             @Valid @RequestBody HeroDto heroDto){
        return ResponseEntity.status(HttpStatus.OK).body(new HeroDto());

    }
}
