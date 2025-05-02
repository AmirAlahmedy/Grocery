import { Component, inject } from '@angular/core';
import { Product } from '../product';
import { CommonModule } from '@angular/common';
import { ProductComponent } from '../product/product.component';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ProductComponent],
  template: `
   <section>
      <form>  
        <input type="text" placeholder="Filter by name" />
        <button class="primary" type="button">Search</button>
      </form>
    </section>
    <section class="results">
      <app-product 
        *ngFor="let product of products"
        [product]="product" ></app-product>
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
