import { Component } from '@angular/core';
import { HEROES } from '../models/HeroesMock';
import { Hero } from '../models/HeroModel';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent {
  public hero: Hero = {
    id: 1,
    name: 'Windstorm'
  };
  public heroes = HEROES;
  public selectedHero?: Hero;
  public onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }
}