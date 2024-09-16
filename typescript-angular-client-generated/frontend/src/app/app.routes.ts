import { Routes } from '@angular/router';
import { FilmInputComponent } from './film-input/film-input.component';
import { FilmListComponent } from './film-list/film-list.component';

export const routes: Routes = [
  { path: 'film-input', component: FilmInputComponent },
  { path: 'film-list', component: FilmListComponent }
];

