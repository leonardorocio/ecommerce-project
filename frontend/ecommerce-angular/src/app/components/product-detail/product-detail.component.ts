import { Component, Input, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { User } from 'src/app/models/user';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  product!: Product;
  user: User = JSON.parse(sessionStorage['user']);

  constructor(
    private titleService: Title,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    const productId = this.activatedRoute.snapshot.params['id'];
    this.productService.getProductById(productId).subscribe(
      (product) => {
        this.product = product;
        this.titleService.setTitle(this.product!.name);
      }
    );
  }

  sendToCart(product: Product) {
    this.router.navigateByUrl(`/carrinho`, {
      state: product,
    });
  }
}
