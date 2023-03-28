package com.example.restwithspring.controller;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.services.HeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/hero/v1")
@Tag(name = "hero", description = "Endpoints for Managing Hero")
public class HeroesController {
    @Autowired
    private HeroService heroService;
    @PostMapping(consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create one hero",
               description = "the objective of this method is to create a hero",
               tags = {"Hero"},
               responses = {
                       @ApiResponse(description = "Created", responseCode = "201", content = {
                             @Content(
                                     mediaType = APPLICATION_JSON_VALUE,
                                     schema = @Schema(implementation = HeroDto.class)
                             )
                       }),
                       @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                       @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                       @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                       @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
               }
    )
    public ResponseEntity<Object> createHero(@RequestBody @Valid HeroDto heroDto){
        var hasHeroInDataBase = heroService.hasHero(heroDto.getName());
        if(hasHeroInDataBase){
            return  ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflito: Existe um heroi cadastrado com este nome");
        }
        var result = heroService.save(heroDto);
        result.add(linkTo(methodOn(HeroesController.class).getHero(result.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE,
                produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All Heroes",
            description = "the objective of this method is to get all the heroes",
            tags = {"Hero"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = HeroDto.class))
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<List<HeroDto>> getAllHeroes(){
        var result = heroService.findAll();
        result.stream().forEach(h -> h.add(linkTo(methodOn(HeroesController.class).getHero(h.getId())).withSelfRel()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/{id}",
                consumes = APPLICATION_JSON_VALUE,
                produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get one hero",
            description = "the objective of this method is to get a hero",
            tags = {"Hero"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HeroDto.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Object> getHero(@PathVariable(value = "id") int id){
        var result = heroService.findById(id);
        if(!result.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Heroi não encontrado");
        }
        result.get().add(linkTo(methodOn(HeroesController.class).getAllHeroes()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete one hero",
            description = "the objective of this method is delete a hero",
            tags = {"Hero"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Object> deleteHero(@PathVariable(value = "id") int id){
        var anyHero = heroService.hasHero(id);
        if(!anyHero){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Heroi não encontrado");
        }
        heroService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Heroi deletado");
    }
    @PutMapping(value = "/{id}",
                consumes = APPLICATION_JSON_VALUE,
                produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update one hero",
            description = "the objective of this method is updated a hero",
            tags = {"Hero"},
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200", content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HeroDto.class)
                            )
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
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
