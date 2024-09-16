import { Component, OnInit } from '@angular/core';
import { FilmService } from '../api/film.service';
import { FilmsService } from '../api/films.service';
import { InlineResponse200 } from '../model/inlineResponse200';
import { Film } from '../model/film';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Import CommonModule for structural directives

@Component({
  selector: 'app-film-input',
  templateUrl: './film-input.component.html',
  styleUrls: ['./film-input.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Use CommonModule here
  providers: [FilmService, FilmsService] // Provide the services here
})
export class FilmInputComponent implements OnInit {
  filmForm: FormGroup;
  films: Film[] = [];

  constructor(private filmService: FilmService, private filmsService: FilmsService, private fb: FormBuilder) {
    // Initialize the form group with form controls for title, length, and mood
    this.filmForm = this.fb.group({
      title: ['', Validators.required],
      length: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Only numbers
      mood: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Fetch the list of films when the component initializes
    this.getFilms();
  }

  // Add a film to the list
  addFilm(): void {
    if (this.filmForm.valid) {
      const newFilm: Film = this.filmForm.value;
      this.filmService.addFilm(newFilm, newFilm.title).subscribe(
        (film: Film) => {
          this.films.push(film); // Add film to the local list
          this.filmForm.reset();  // Reset the form after successful submission
        },
        (error) => {
          console.error('Error adding film:', error);
        }
      );
    }
  }

  // Delete a film by ID
  deleteFilm(id: string): void {
    this.filmService.deleteFilm(id).subscribe({
      next: () => {
        // Success case: Remove the film from the list
        this.films = this.films.filter(film => film.id !== id);
      },
      error: (error) => {
        // Error case: Log the error
        console.error('Error deleting film:', error);
      },
      complete: () => {
        console.log(`Film with id ${id} was successfully deleted`);
      }
    });
  }

  // Fetch all films from the service
  getFilms(): void {
    this.filmsService.getFilms().subscribe({
      next: (response: InlineResponse200) => {
        // Assuming the films are located inside a property like 'items' or 'data'
        this.films = response.data || []; // Adjust 'items' to whatever property contains the array of films
      },
      error: (error) => {
        console.error('Error fetching films:', error);
      }
    });
  }
}
