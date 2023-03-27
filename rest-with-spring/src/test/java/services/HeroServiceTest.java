package services;

import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.model.Hero;
import com.example.restwithspring.repositories.HeroRepository;
import com.example.restwithspring.services.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import services.mocks.MockHero;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {
    @InjectMocks
    private HeroService heroService;
    @Mock
    private HeroRepository heroRepository;
    MockHero input;

    @BeforeEach
    void setUpMocks() {
        input = new MockHero();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void findById_shouldBe_Equals(){
        assertDoesNotThrow(()->{
            var heroMock = input.mockEntity(1);
            heroMock.setId(1);
            when(heroRepository.findById(heroMock.getId())).thenReturn(Optional.of(heroMock));
            var result = heroService.findById(1);

            var expected = Optional.of(this.HeroToHeroDto(heroMock));
            assertEquals(result,expected);
        });
    }
    @Test
    void create_shouldBe_Equals(){
        assertDoesNotThrow(()->{
            var heroDto = input.mockDto(1);
            var entity = this.HeroDtoToHero(heroDto);
            var entityPersisted = entity;
            entityPersisted.setId(1);
            when(heroRepository.save(any())).thenReturn(entityPersisted);
            var result = heroService.save(heroDto);
            heroDto.setId(1);
            assertEquals(result,heroDto);
        });
    }
    @Test
    public void findAll_shouldBe_sizeGreaterZero(){
        assertDoesNotThrow(() -> {
            var heroesList = input.mockHeroList(10);
            when(heroRepository.findAll()).thenReturn(heroesList);
            var result = heroService.findAll();
            var expected = new ArrayList<HeroDto>();
            for (var entity: heroesList) {
                var heroDto = this.HeroToHeroDto(entity);
                expected.add(heroDto);
            }
            assertEquals(result, expected);
        });
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
