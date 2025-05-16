import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-checkout',
  template: `
    <h1>Checkout</h1>
    
  `,
  styles: [`
    h1 {
      margin-bottom: 30px;
    }
    .checkout-container {
      display: grid;
      grid-template-columns: 1fr 350px;
      gap: 30px;
    }
    .checkout-form {
      background: white;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 20px;
    }
    .checkout-form h2 {
      margin-top: 0;
      margin-bottom: 20px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    .form-group input {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 1rem;
    }
    .form-row {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
      gap: 15px;
    }
    .payment-methods {
      margin-bottom: 20px;
    }
    .payment-method {
      display: flex;
      align-items: center;
      padding: 10px 15px;
      border: 1px solid #ddd;
      border-radius: 4px;
      margin-bottom: 10px;
      cursor: pointer;
      transition: all 0.2s;
    }
    .payment-method.selected {
      border-color: #4CAF50;
      background-color: rgba(76, 175, 80, 0.1);
    }
    .payment-method input {
      margin-right: 10px;
    }
    .card-info {
      margin-top: 15px;
      padding: 15px;
      border: 1px solid #ddd;
      border-radius: 4px;
      background-color: #f9f9f9;
    }
    .place-order-btn {
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
    .place-order-btn:hover {
      background-color: #45a049;
    }
    .place-order-btn:disabled {
      background-color: #cccccc;
      cursor: not-allowed;
    }
    .order-summary {
      background: white;
      border-radius: 5px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
      padding: 20px;
      position: sticky;
      top: 20px;
    }
    .order-summary h2 {
      margin-top: 0;
      margin-bottom: 20px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    .order-items {
      margin-bottom: 15px;
      max-height: 300px;
      overflow-y: auto;
    }
    .order-item {
      display: flex;
      justify-content: space-between;
      padding: 10px 0;
      border-bottom: 1px solid #eee;
    }
    .order-item:last-child {
      border-bottom: none;
    }
    .item-info {
      display: flex;
    }
    .item-quantity {
      font-weight: bold;
      margin-right: 10px;
    }
    .item-price {
      font-weight: bold;
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
    .continue-shopping-btn {
      background-color: #FF5722;
      color: white;
      border: none;
      padding: 12px 20px;
      border-radius: 4px;
      cursor: pointer;
      font-size: 1rem;
      transition: background-color 0.3s;
    }
    .continue-shopping-btn:hover {
      background-color: #e64a19;
    }
    .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0, 0, 0, 0.7);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
    }
    .modal {
      background: white;
      border-radius: 5px;
      padding: 30px;
      max-width: 500px;
      width: 90%;
      text-align: center;
    }
    .modal h2 {
      margin-top: 0;
      color: #4CAF50;
    }
    @media (max-width: 768px) {
      .checkout-container {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class CheckoutComponent implements OnInit {
  cartItems: CartItem[] = [];
  isSubmitting = false;
  showConfirmation = false;
  orderNumber = '';

  shippingInfo = {
    fullName: '',
    email: '',
    phone: '',
    address: '',
    city: '',
    state: '',
    zipCode: ''
  };

  paymentMethod = 'credit';
  paymentInfo = {
    cardName: '',
    cardNumber: '',
    expiration: '',
    cvv: ''
  };

  constructor(
    private router: Router,
    private cartService: CartService
  ) { }

  ngOnInit(): void {
    this.cartService.getCart().subscribe(items => {
      this.cartItems = items;
    });
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

  placeOrder(): void {
    this.isSubmitting = true;
    
    // Simulate API call with setTimeout
    setTimeout(() => {
      this.orderNumber = 'ORD' + Math.floor(100000 + Math.random() * 900000);
      this.isSubmitting = false;
      this.showConfirmation = true;
      this.cartService.clearCart();
    }, 1500);
  }

  closeConfirmation(): void {
    this.showConfirmation = false;
    this.router.navigate(['/']);
  }
}