import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FilmListComponent } from './film-list/film-list.component';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';

@NgModule({
    declarations: [AppComponent], // Declare AppComponent
  imports: [BrowserModule, CommonModule, FilmListComponent, RouterModule.forRoot(routes)], // Import CommonModule for common directives like NgIf and NgFor
  providers: [FilmListComponent],
  bootstrap: [AppComponent] // Bootstrap AppComponent
})
export class AppComponent {
  title = 'frontend';
}
