package mocks;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.model.Hero;

import java.util.ArrayList;

public class MockHero {

    public ArrayList<Hero> mockHeroList(int count)
    {
        var result = new ArrayList<Hero>();
        for (int i = 1; i <= count; i++){
            var entity = this.mockEntity(i);
            result.add(entity);
        }
        return result;
    }
    public HeroDto mockDto(int i) {
        var dto = new HeroDto();
        dto.setName("Hero dto teste " + i);
        return dto;
    }

    public Hero mockEntity(int i) {
        var hero = new Hero();
        hero.setName("Hero Teste " + i);
        return hero;
    }
}
