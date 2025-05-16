import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  template: `
    <h1>Your Shopping Cart</h1>

  `,
  styles: [`
    h1 {
      margin-bottom: 30px;
    }
    .cart-container {
      display: grid;
      grid-template-columns: 1fr 350px;
      gap: 30px;
    }
    .cart-items {
      background: white;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 20px;
    }
    .cart-item {
      display: grid;
      grid-template-columns: 100px 1fr 120px 100px 40px;
      align-items: center;
      gap: 15px;
      padding: 15px 0;
      border-bottom: 1px solid #eee;
    }
    .cart-item:last-child {
      border-bottom: none;
    }
    .item-image img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 4px;
    }
    .item-details h3 {
      margin-top: 0;
      margin-bottom: 5px;
      font-size: 1.1rem;
    }
    .item-price {
      color: #666;
    }
    .item-quantity {
      display: flex;
      align-items: center;
    }
    .item-quantity button {
      width: 30px;
      height: 30px;
      background-color: #f5f5f5;
      border: 1px solid #ddd;
      border-radius: 3px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
    .item-quantity button:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
    .item-quantity span {
      width: 40px;
      text-align: center;
    }
    .item-total {
      font-weight: bold;
    }
    .remove-btn {
      background: none;
      border: none;
      color: #F44336;
      cursor: pointer;
      font-size: 1.2rem;
      padding: 0;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .cart-summary {
      background: white;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 20px;
      position: sticky;
      top: 20px;
    }
    .cart-summary h2 {
      margin-top: 0;
      margin-bottom: 20px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .summary-row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }
    .summary-row.total {
      font-weight: bold;
      font-size: 1.2rem;
      border-top: 1px solid #eee;
      padding-top: 10px;
      margin-top: 10px;
    }
    .checkout-btn {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 12px 0;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      transition: background-color 0.3s;
      width: 100%;
      margin-top: 20px;
    }
    .checkout-btn:hover {
      background-color: #45a049;
    }
    .continue-shopping-btn {
      background-color: #FF5722;
      color: white;
      border: none;
      padding: 12px 0;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      transition: background-color 0.3s;
      width: 100%;
      margin-top: 10px;
    }
    .continue-shopping-btn:hover {
      background-color: #e64a19;
    }
    .empty-cart {
      background: white;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 40px;
      text-align: center;
    }
    .empty-cart-message h2 {
      margin-top: 0;
    }
    .empty-cart-message p {
      color: #666;
      margin-bottom: 30px;
    }
    @media (max-width: 768px) {
      .cart-container {
        grid-template-columns: 1fr;
      }
      .cart-item {
        grid-template-columns: 80px 1fr 80px;
        grid-template-rows: auto auto;
      }
      .item-quantity {
        grid-column: 2;
        grid-row: 2;
      }
      .item-total {
        grid-column: 3;
        grid-row: 1 / 3;
        align-self: center;
      }
      .remove-btn {
        grid-column: 3;
        grid-row: 1;
        justify-self: end;
      }
    }
  `]
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];

  constructor(
    private router: Router,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.cartService.getCart().subscribe(items => {
      this.cartItems = items;
    });
  }

  increaseQuantity(productId: number): void {
    const item = this.cartItems.find(item => item.product.id === productId);
    if (item) {
      this.cartService.updateQuantity(productId, item.quantity + 1);
    }
  }

  decreaseQuantity(productId: number): void {
    const item = this.cartItems.find(item => item.product.id === productId);
    if (item && item.quantity > 1) {
      this.cartService.updateQuantity(productId, item.quantity - 1);
    }
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(productId);
  }

  getSubtotal(): number {
    return this.cartItems.reduce(
      (total, item) => total + (item.product.price * item.quantity), 
      0
    );
  }

  getShippingFee(): number {
    const subtotal = this.getSubtotal();
    // Free shipping for orders over $50
    return subtotal >= 50 ? 0 : 5.99;
  }

  getTotal(): number {
    return this.getSubtotal() + this.getShippingFee();
  }

  continueShopping(): void {
    this.router.navigate(['/']);
  }

  goToCheckout(): void {
    this.router.navigate(['/checkout']);
  }
}