import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent {
  constructor(private router: Router) {}

  @Input() product!: Product;

  goToDetail(product: Product) {
    this.router.navigateByUrl(`/produto/${product.productId}`, {
      state: product,
    });
  }

  sendToCart(product: Product) {
    this.router.navigateByUrl(`/carrinho`, {
      state: product,
    });
  }
}
