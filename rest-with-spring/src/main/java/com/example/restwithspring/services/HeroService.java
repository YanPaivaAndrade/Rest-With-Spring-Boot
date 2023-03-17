package com.example.restwithspring.services;

import com.example.restwithspring.model.Hero;
import com.example.restwithspring.repositories.IHeroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HeroService {

    private static IHeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Transactional
    public static Hero save(Hero hero) {
        return  heroRepository.save(hero);
    }

    public boolean hasHero(String name) {
        return heroRepository.existsByName(name);
    }

    public List<Hero> findAll() {
        return heroRepository.findAll();
    }

    public Optional<Hero> findById(int id) {
        return heroRepository.findById(id);
    }

    @Transactional
    public void delete(Hero hero) {
        heroRepository.delete(hero);
    }
}

