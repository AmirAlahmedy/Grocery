import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  template: `
    <div class="search-container">
      <input 
        type="text" 
        placeholder="Search products..." 
        [(ngModel)]="searchTerm"
        (keyup.enter)="search()"
      >
      <button class="search-button" (click)="search()">🔍</button>
    </div>
  `,
  styles: [`
    .search-container {
      display: flex;
      flex-grow: 1;
      max-width: 500px;
      margin: 0 20px;
    }
    input {
      flex-grow: 1;
      padding: 10px;
      border: none;
      border-radius: 4px 0 0 4px;
      font-size: 1rem;
    }
    .search-button {
      background-color: #FF5722;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 0 4px 4px 0;
      cursor: pointer;
    }
  `]
})
export class SearchBarComponent {
  searchTerm = '';

  constructor(private router: Router) { }

  search(): void {
    if (this.searchTerm.trim()) {
      this.router.navigate(['/'], { 
        queryParams: { search: this.searchTerm }
      });
    }
  }
}