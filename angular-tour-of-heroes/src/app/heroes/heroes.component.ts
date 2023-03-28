import { Component } from '@angular/core';
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
}