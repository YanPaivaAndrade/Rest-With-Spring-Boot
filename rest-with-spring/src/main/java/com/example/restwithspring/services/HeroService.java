package com.example.restwithspring.services;
import com.example.restwithspring.model.Hero;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeroService {

    public static Hero save(Hero hero) {
        return new Hero();
    }

    public boolean hasHero(String name) {
        return true;
    }

    public List<Hero> findAll() {
        return new ArrayList<>();
    }

    public Optional<Hero> findById(int id) {
        return null;
    }

    public void delete(Hero hero) {
    }
}
