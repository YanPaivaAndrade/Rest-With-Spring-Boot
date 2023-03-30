import { Component, OnInit } from '@angular/core';
import { Hero } from '../models/HeroModel';
import { HeroService } from '../hero.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})
export class HeroesComponent implements OnInit  {
 
  public heroes = new Array();
  public selectedHero?: Hero;
  constructor(private heroService: HeroService) {

  }
  public ngOnInit(): void {
    this.getHeroes();
  }
  public add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.heroService.addHero({ name } as Hero)
      .subscribe(hero => {
        this.heroes.push(hero);
      });
  }

  private getHeroes(): void {
    this.heroService
          .getHeroes()
          .subscribe(heroes => this.heroes = heroes);
  }
 
}