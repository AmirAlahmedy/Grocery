import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-category-filter',
  template: `
    <div class="category-filter">
      <h3>Categories</h3>
      <ul>
        <li 
          [class.active]="selectedCategory === 'all'"
          (click)="selectCategory('all')"
        >
          All Products
        </li>
        <li 
          *ngFor="let category of categories" 
          [class.active]="selectedCategory === category.value"
          (click)="selectCategory(category.value)"
        >
          {{ category.label }}
        </li>
      </ul>
    </div>
  `,
  styles: [`
    .category-filter {
      background-color: #f8f8f8;
      padding: 15px;
      border-radius: 5px;
      margin-bottom: 20px;
    }
    h3 {
      margin-top: 0;
      margin-bottom: 15px;
      color: #333;
    }
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }
    li {
      padding: 8px 0;
      cursor: pointer;
      transition: all 0.2s;
      border-bottom: 1px solid #eee;
    }
    li:hover {
      color: #4CAF50;
    }
    li.active {
      color: #4CAF50;
      font-weight: bold;
    }
  `]
})
export class CategoryFilterComponent implements OnInit {
  @Output() categoryChange = new EventEmitter<string>();
  
  categories = [
    { label: 'Fruits', value: 'fruits' },
    { label: 'Vegetables', value: 'vegetables' },
    { label: 'Dairy', value: 'dairy' },
    { label: 'Bakery', value: 'bakery' },
    { label: 'Meat', value: 'meat' }
  ];
  
  selectedCategory = 'all';

  constructor() { }

  ngOnInit(): void {
  }

  selectCategory(category: string): void {
    this.selectedCategory = category;
    this.categoryChange.emit(category);
  }
}