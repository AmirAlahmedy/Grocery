import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, SearchBarComponent],
  template: `
    <header class="header card-surface">
      <div class="header-inner">
        <div class="left">
          <div class="logo" (click)="navigateHome()" aria-label="Go home">
            <div class="logo-badge">🧺</div>
            <span class="logo-text">Fresh Basket</span>
          </div>
        </div>

        <div class="center">
          <app-search-bar></app-search-bar>
        </div>

        <div class="right nav-items">
          <button class="btn-ghost cart-button" (click)="navigateToCart()" aria-label="View cart">
            <span class="cart-emoji">🛒</span>
            <span class="cart-count" *ngIf="cartItemCount > 0">{{ cartItemCount }}</span>
          </button>
        </div>
      </div>
    </header>
  `,
  styles: [`
    .header {
      position: sticky;
      top: 0;
      z-index: 60;
      background: transparent;
      border-bottom: 1px solid rgba(96,93,200,0.04);
    }

    .header-inner {
      display: flex;
      align-items: center;
      justify-content: space-between;
      gap: 12px;
      width: 100%;
      max-width: var(--content-max-width);
      margin: 0 auto;
      padding: 10px 18px;
    }

    .logo {
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .logo-badge {
      width: 44px;
      height: 44px;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      border-radius: 10px;
      background: linear-gradient(180deg, var(--secondary-color), var(--primary-color));
      color: white;
      font-size: 1.15rem;
      box-shadow: 0 6px 18px rgba(96,93,200,0.12);
    }

    .logo-text {
      font-size: 1.1rem;
      font-weight: 800;
      color: var(--primary-color);
      letter-spacing: -0.5px;
    }

    .center {
      flex: 1 1 420px;
      display: flex;
      justify-content: center;
    }

    .nav-items {
      display: flex;
      align-items: center;
    }

    .cart-button {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 8px 12px;
      border-radius: 12px;
      border: 1px solid rgba(96,93,200,0.10);
      background: white;
      cursor: pointer;
      transition: transform .12s ease, box-shadow .12s ease;
      position: relative;
    }

    .cart-button:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(96,93,200,0.08); }

    .cart-emoji { font-size: 1.25rem; }

    .cart-count {
      position: absolute;
      top: -6px;
      right: -6px;
      background-color: #FF5722;
      color: white;
      border-radius: 50%;
      width: 20px;
      height: 20px;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      font-size: 0.75rem;
      font-weight: 700;
      box-shadow: 0 3px 10px rgba(0,0,0,0.08);
    }

    @media (max-width: 700px) {
      .center { order: 3; width: 100%; justify-content: flex-start; }
      .header-inner { padding: 10px 12px; }
      .logo-text { font-size: 1rem }
      .logo-badge { width: 40px; height: 40px }
      .cart-button { padding: 8px }
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
