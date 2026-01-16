import { Component, inject } from '@angular/core';
import { Product } from '../../models/product';
import { CommonModule } from '@angular/common';
import { ProductCardComponent } from '../../components/product-card/product-card.component';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ProductCardComponent],
  template: `
    <section class="results">
      <app-product-card 
        *ngFor="let product of products"
        [product]="product" ></app-product-card>
    </section>

  `,
  styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  products: Product[] = [];
  productService: ProductService = inject(ProductService);

  constructor() {
    this.products = this.productService.getAllProducts();
  }
}
