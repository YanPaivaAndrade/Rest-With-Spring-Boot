package com.example.restwithspring.services;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.model.Hero;
import com.example.restwithspring.repositories.HeroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Transactional
    public HeroDto save(HeroDto heroDto) {
        var entity = this.HeroDtoToHero(heroDto);
        var heroSaved = heroRepository.save(entity);
        var result = this.HeroToHeroDto(heroSaved);
        return  result;
    }

    public boolean hasHero(String name) {
        var result = heroRepository.existsByName(name);
        return result;
    }
    public boolean hasHero(int id) {
        var result = heroRepository.existsById(id);
        return result;
    }
    public List<HeroDto> findAll() {
        var entityList = heroRepository
                            .findAll();
        var resultList = new ArrayList<HeroDto>();
        for (var entity: entityList) {
            var heroDto = this.HeroToHeroDto(entity);
            resultList.add(heroDto);
        }
        return resultList;
    }

    public Optional<HeroDto> findById(int id) {
        var entity = heroRepository.findById(id);
        var result = entity.map(h -> this.HeroToHeroDto(h));
        return result;
    }

    @Transactional
    public void delete(int id) {
        var entity = heroRepository.findById(id);
        if(entity.isPresent()){
            heroRepository.delete(entity.get());
        }
    }

    private HeroDto HeroToHeroDto(Hero entity){
        var result = new HeroDto();
        BeanUtils.copyProperties(entity, result);
        return result;
    }
    private Hero HeroDtoToHero(HeroDto dto){
        var result = new Hero();
        BeanUtils.copyProperties(dto, result);
        return result;
    }
}

