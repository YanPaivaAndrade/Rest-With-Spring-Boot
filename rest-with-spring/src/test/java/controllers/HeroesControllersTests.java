package controllers;

import com.example.restwithspring.controller.HeroesController;
import com.example.restwithspring.dtos.HeroDto;
import com.example.restwithspring.services.HeroService;
import mocks.MockHero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class HeroesControllersTests {
    @InjectMocks
    private HeroesController heroesController;
    @Mock
    private HeroService heroService;
    MockHero input;

    @BeforeEach
    void setUpMocks() {
        input = new MockHero();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void createHero_shouldBe_conflict(){
        assertDoesNotThrow(()->{
            var heroMock = input.mockDto(1);
            when(heroService.hasHero(any())).thenReturn(true);
            var result = heroesController.createHero(heroMock);
            var expected = HttpStatus.CONFLICT;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void createHero_shouldBe_created(){
        assertDoesNotThrow(()->{
            var heroMock = input.mockDto(1);
            when(heroService.hasHero(any())).thenReturn(false);
            when(heroService.save(heroMock)).thenReturn(heroMock);
            var result = heroesController.createHero(heroMock);
            var expected = HttpStatus.CREATED;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void getAll_shouldBe_ok(){
        assertDoesNotThrow(()->{
            when(heroService.findAll()).thenReturn(new ArrayList<HeroDto>());
            var result = heroesController.getAllHeroes();
            var expected = HttpStatus.OK;
            assertEquals(result.getStatusCode(),expected);
        });
    }

    @Test
    public void getHero_shouldBe_notFound(){
        assertDoesNotThrow(()->{
            when(heroService.findById(anyInt())).thenReturn(Optional.empty());
            var result = heroesController.getHero(anyInt());
            var expected = HttpStatus.NOT_FOUND;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void getHero_shouldBe_ok(){
        assertDoesNotThrow(()->{
            var heroMock = input.mockDto(1);
            when(heroService.findById(anyInt())).thenReturn(Optional.of(heroMock));
            var result = heroesController.getHero(anyInt());
            var expected = HttpStatus.OK;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void deleteHero_shouldBe_notFound(){
        assertDoesNotThrow(()->{
            when(heroService.hasHero(anyInt())).thenReturn(false);
            var result = heroesController.deleteHero(anyInt());
            var expected = HttpStatus.NOT_FOUND;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void deleteHero_shouldBe_noContent(){
        assertDoesNotThrow(()->{
            when(heroService.hasHero(anyInt())).thenReturn(true);
            doNothing().when(heroService).delete(anyInt());
            var result = heroesController.deleteHero(anyInt());
            var expected = HttpStatus.NO_CONTENT;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void updateHero_shouldBe_notFound(){
        assertDoesNotThrow(()->{
            var heroDtoMock = input.mockDto(1);
            when(heroService.hasHero(anyInt())).thenReturn(false);
            var result = heroesController.updateHero(anyInt(), heroDtoMock);
            var expected = HttpStatus.NOT_FOUND;
            assertEquals(result.getStatusCode(),expected);
        });
    }
    @Test
    public void updateHero_shouldBe_noContent(){
        assertDoesNotThrow(()->{
            var heroDtoMock = input.mockDto(1);
            when(heroService.hasHero(anyInt())).thenReturn(true);
            when(heroService.save(heroDtoMock)).thenReturn(heroDtoMock);
            var result = heroesController.updateHero(anyInt(), heroDtoMock);
            var expected = HttpStatus.OK;
            assertEquals(result.getStatusCode(),expected);
        });
    }
}
