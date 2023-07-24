import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Product } from 'src/app/models/product';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { Favorite } from 'src/app/models/favorite';
import { FavoriteService } from 'src/app/services/favorite.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css'],
})
export class FavoritesComponent implements OnInit {
  constructor(
    private userService: UserService,
    private cookieService: CookieService,
    private favoriteService: FavoriteService,
    private router: Router
  ) {}

  faHeart = faHeart;
  favorites: Favorite[] = [];
  user: User = JSON.parse(this.cookieService.get('user'));

  ngOnInit(): void {
    this.userService.getUsersFavorites(this.user.id).subscribe((favorites) => {
      this.favorites = favorites;
    });
  }

  unfavorite(favoriteToDelete: Favorite) {
    this.favoriteService.deleteFavorite(favoriteToDelete.id).subscribe((data) => {
      this.favorites = this.favorites.filter((favorite) => favorite.id !== favoriteToDelete.id);
    });
  }

  goToDashboard() {
    this.router.navigateByUrl('/dashboard#home');
  }

  sendToCart(product: Product) {
    this.cookieService.set("product", JSON.stringify(product));
    this.router.navigateByUrl(`/carrinho`);
  }
}
