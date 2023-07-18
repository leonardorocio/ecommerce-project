import {
  Component,
  DoCheck,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { faHeart as faHeartRegular } from '@fortawesome/free-regular-svg-icons';
import { FavoriteService } from 'src/app/services/favorite.service';
import { CookieService } from 'ngx-cookie-service';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';
import { Favorite } from 'src/app/models/favorite';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent implements DoCheck {
  faHeart = faHeart;
  faHeartRegular = faHeartRegular;

  @Input() userFavorite?: Favorite;
  @Input() product!: Product;
  isUserFavorite: boolean = false;

  constructor(
    private router: Router,
    private favoriteService: FavoriteService,
    private cookieService: CookieService,
    private toastr: ToastrService
  ) {}

  ngDoCheck(): void {
      const changes = this.userFavorite !== undefined && this.userFavorite !== null;
      this.isUserFavorite = changes;
  }

  goToDetail(product: Product) {
    this.router.navigateByUrl(`/produto/${product.id}`, {
      state: product,
    });
  }

  sendToCart(product: Product) {
    this.router.navigateByUrl(`/carrinho`, {
      state: product,
    });
  }

  favoriteProduct(product: Product) {
    if (this.cookieService.check('user')) {
      if (this.isUserFavorite) {
        const favoriteId: number = this.userFavorite?.id as number;
        this.favoriteService.deleteFavorite(favoriteId).subscribe((data) => {
          this.userFavorite = undefined;
        });
      } else {
        const requestBody = {
          productId: product.id,
          userId: JSON.parse(this.cookieService.get('user')).id,
        };
        this.favoriteService.createFavorite(requestBody).subscribe((data) => {
          if (data !== null && data !== undefined) {
            this.userFavorite = data;
          }
        });
      }
    }
  }
}
