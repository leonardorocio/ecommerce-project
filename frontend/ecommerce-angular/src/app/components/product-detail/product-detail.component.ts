import { Component, Input, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
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
  user?: User;

  constructor(
    private titleService: Title,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.user = this.cookieService.check("user") ? JSON.parse(this.cookieService.get("user")) : undefined;
    const productId = this.activatedRoute.snapshot.params['id'];
    this.productService.getProductById(productId).subscribe(
      (product) => {
        this.product = product;
        this.titleService.setTitle(this.product!.name);
      }
    );
  }

  sendToCart(product: Product) {
    this.cookieService.set("product", JSON.stringify(product));
    this.router.navigateByUrl(`/carrinho`);
  }
}
