import { Injectable } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  readonly baseUrl = 'https://angular.dev/assets/images/tutorials/common';

  products = [
    {
      id: 1,
      name: 'Apple',
      price: 1.5,
      description: 'Fresh and juicy apples',
      imageUrl: `${this.baseUrl}/example-house.jpg`,
      category: 'Fruits',
      stock: 100,
      rating: 4.5,
      reviews: 50,
    },
    {
      id: 2,
      name: 'Banana',
      price: 0.8,
      description: 'Sweet and ripe bananas',
      imageUrl: `${this.baseUrl}/example-house.jpg`,
      category: 'Fruits',
      stock: 200,
      rating: 4.7,
      reviews: 30,
    },
    {
      id: 3,
      name: 'Carrot',
      price: 0.5,
      description: 'Fresh and crunchy carrots',
      imageUrl: `${this.baseUrl}/example-house.jpg`,
      category: 'Vegetables',
      stock: 150,
      rating: 4.3,
      reviews: 20,
    },
    {
      id: 4,
      name: 'Tomato',
      price: 1.2,
      description: 'Juicy and ripe tomatoes',
      imageUrl: `${this.baseUrl}/example-house.jpg`,
      category: 'Vegetables',
      stock: 120,
      rating: 4.6,
      reviews: 40,
    },
    {
      id: 5,
      name: 'Orange',
      price: 1.8,
      description: 'Citrusy and fresh oranges',
      imageUrl: `${this.baseUrl}/example-house.jpg`,
      category: 'Fruits',
      stock: 80,
      rating: 4.8,
      reviews: 60,
    },
  ];

  constructor() { }

  getAllProducts(): Product[] {
    return this.products;
  }
  getProductById(id: number): Product | undefined {
    return this.products.find((product) => product.id === id);
  }
}
