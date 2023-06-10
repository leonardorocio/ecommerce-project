import { Component, Input, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  product!: Product;
  private user: User = JSON.parse(localStorage['user']);

  constructor(private titleService: Title, private router: Router) {}

  ngOnInit(): void {
    this.product = history.state;
    this.titleService.setTitle(this.product!.name);
  }

  sendToCart(product: Product) {
    this.router.navigateByUrl(`/carrinho`, {
      state: product,
    });
  }
}
