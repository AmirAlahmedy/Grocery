import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="footer">
      <div class="footer-content">
        <div class="footer-section">
          <h3>Fresh Basket</h3>
          <p>Your one-stop online grocery store</p>
        </div>
        <div class="footer-section">
          <h3>Quick Links</h3>
          <ul>
            <li><a href="#">About Us</a></li>
            <li><a href="#">Contact</a></li>
            <li><a href="#">FAQ</a></li>
            <li><a href="#">Terms of Service</a></li>
          </ul>
        </div>
        <div class="footer-section">
          <h3>Contact Us</h3>
          <p>Email: support&#64;freshbasket.com</p>
          <p>Phone: (555) 123-4567</p>
        </div>
      </div>
      <div class="copyright">
        © {{ currentYear }} Fresh Basket. All rights reserved.
      </div>
    </footer>
  `,
  styles: [`
    .footer {
      background-color: #333;
      color: white;
      padding: 20px;
      margin-top: 30px;
    }
    .footer-content {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      max-width: 1200px;
      margin: 0 auto;
    }
    .footer-section {
      flex: 1;
      min-width: 200px;
      margin-bottom: 20px;
    }
    .footer-section h3 {
      margin-bottom: 15px;
      color: #4CAF50;
    }
    .footer-section ul {
      list-style: none;
      padding: 0;
    }
    .footer-section ul li {
      margin-bottom: 8px;
    }
    .footer-section a {
      color: #ddd;
      text-decoration: none;
    }
    .footer-section a:hover {
      color: #4CAF50;
    }
    .copyright {
      text-align: center;
      padding-top: 20px;
      border-top: 1px solid #444;
      margin-top: 20px;
    }
  `]
})
export class FooterComponent {
  currentYear = new Date().getFullYear();
}