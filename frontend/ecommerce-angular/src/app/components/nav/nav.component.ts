import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent {
  @Input() userId!: number;

  constructor(private router: Router, private productService: ProductService) {}

  goToSearchProducts(term: string) {
    this.productService.nextTerm(term);
    this.router.navigate(['/search'], { queryParams: { name: term } });
  }
}
