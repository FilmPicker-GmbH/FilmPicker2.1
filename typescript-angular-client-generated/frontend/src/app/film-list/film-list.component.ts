import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilmsService } from '../api/films.service';
import { Film } from '../model/film';
import { NgForOf, NgIf } from '@angular/common'; // Import necessary directives

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css'],
  standalone: true,
  imports: [CommonModule, NgForOf, NgIf], // Include CommonModule and necessary directives
  providers: [FilmsService] // Provide the service here
})
export class FilmListComponent implements OnInit {
  films: Film[] = [];
  currentPage: number = 1;
  pageSize: number = 10;
  totalFilms: number = 0;

  constructor(private filmsService: FilmsService) { }

  ngOnInit(): void {
    this.getFilms();
  }

  getFilms(): void {
    this.filmsService.getFilms(this.currentPage, this.pageSize).subscribe(response => {
      this.films = response.data || [];
      this.totalFilms = response.total || 0;
    });
  }

  changePage(newPage: number): void {
    if (newPage > 0 && newPage * this.pageSize <= this.totalFilms) {
      this.currentPage = newPage;
      this.getFilms();
    }
  }
}
