package com.example.restwithspring.repositories;

import com.example.restwithspring.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHeroRepository extends JpaRepository<Hero, Integer> {
    boolean existsByName(String name);
}
