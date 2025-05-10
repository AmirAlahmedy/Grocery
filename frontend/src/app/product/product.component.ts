import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../product';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-product',
  imports: [RouterLink],
  template: `
  <section class="listing">
      <img
        class="listing-photo"
        [src]="product.imageUrl"
        alt="Exterior photo of {{ product.name }}"
        crossorigin
      />
      <h2 class="listing-heading">{{ product.name }}</h2>
      <p class="listing-location">{{ product.price }}, {{ product.rating }}</p>
      <a [routerLink]="['/details', product.id]">Learn More</a>
    </section>
  `,
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {
  @Input() product!: Product;
}
