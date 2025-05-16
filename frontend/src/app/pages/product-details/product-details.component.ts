import { Component, inject } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CommonModule} from '@angular/common';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';

@Component({
  selector: 'app-product-details',
  imports: [],
  template: `
    <article>
      <img
        class="listing-photo"
        [src]="product?.imageUrl"
        alt="Exterior photo of {{ product?.name }}"
        crossorigin
      />
      <section class="listing-description">
        <h2 class="listing-heading">{{ product?.name }}</h2>
        <p class="listing-location">{{ product?.category }}, {{ product?.description }}</p>
      </section>
      <section class="listing-features">
        <h2 class="section-heading">About this product</h2>
        <ul>
          <li>Stock available: {{ product?.stock }}</li>
          <li>Price: {{ product?.price }}</li>
          <li>Rating: {{ product?.rating }}</li>
        </ul>
      </section>
    </article>
  `,
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent {
  route: ActivatedRoute = inject(ActivatedRoute);
  productService: ProductService = inject(ProductService);
  product: Product | undefined;

  constructor() {
    const productId = Number(this.route.snapshot.params['id']); 
    this.product = this.productService.getProductById(productId);
  }
}
