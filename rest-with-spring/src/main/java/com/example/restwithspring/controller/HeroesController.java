package com.example.restwithspring.controller;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.services.HeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
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
        result.add(linkTo(methodOn(HeroesController.class).getHero(result.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<HeroDto>> getAllHeroes(){
        var result = heroService.findAll();
        result.stream().forEach(h -> h.add(linkTo(methodOn(HeroesController.class).getHero(h.getId())).withSelfRel()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getHero(@PathVariable(value = "id") int id){
        var result = heroService.findById(id);
        if(!result.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Heroi não encontrado");
        }
        result.get().add(linkTo(methodOn(HeroesController.class).getAllHeroes()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHero(@PathVariable(value = "id") int id){
        var anyHero = heroService.hasHero(id);
        if(!anyHero){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Heroi não encontrado");
        }
        heroService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Heroi deletado");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateHero(@PathVariable(value = "id") int id,
                                             @Valid @RequestBody HeroDto heroDto){
        var hasHeroInDataBase = heroService.hasHero(id);
        if(!hasHeroInDataBase){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Heroi não encontrado");
        }
        var result = heroService.save(heroDto);
        result.add(linkTo(methodOn(HeroesController.class).getAllHeroes()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
