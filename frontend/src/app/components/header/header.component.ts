import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [CommonModule, SearchBarComponent],
  template: `
    <header class="header">
      <div class="logo" (click)="navigateHome()">
        <span class="logo-text">Fresh Basket</span>
      </div>
      <app-search-bar></app-search-bar>
      <div class="nav-items">
        <div class="cart-icon" (click)="navigateToCart()">
          🛒 <span class="cart-count" *ngIf="cartItemCount > 0">{{ cartItemCount }}</span>
        </div>
      </div>
    </header>
  `,
  styles: [`
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 20px;
      background-color: #4CAF50;
      color: white;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .logo {
      cursor: pointer;
      display: flex;
      align-items: center;
    }
    .logo-text {
      font-size: 1.5rem;
      font-weight: bold;
      margin-left: 10px;
    }
    .nav-items {
      display: flex;
      align-items: center;
    }
    .cart-icon {
      font-size: 1.5rem;
      cursor: pointer;
      position: relative;
      margin-left: 20px;
    }
    .cart-count {
      position: absolute;
      top: -10px;
      right: -10px;
      background-color: #FF5722;
      color: white;
      border-radius: 50%;
      width: 20px;
      height: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 0.8rem;
    }
  `]
})
export class HeaderComponent implements OnInit {
  cartItemCount = 0;

  constructor(
    private router: Router,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.cartService.getCart().subscribe(() => {
      this.cartItemCount = this.cartService.getItemCount();
    });
  }

  navigateHome(): void {
    this.router.navigate(['/']);
  }

  navigateToCart(): void {
    this.router.navigate(['/cart']);
  }
}
