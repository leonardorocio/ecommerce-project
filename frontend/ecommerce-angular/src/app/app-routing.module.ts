import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { UserComponent } from './components/user/user.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    component: AuthComponent,
    title: 'Ecommerce - Autenticação'
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    title: 'Ecommerce - Início'
  },
  {
    path: 'produto/:id',
    component: ProductDetailComponent,
    title: 'Ecommerce - Produto'
  },
  {
    path: 'carrinho',
    component: CartComponent,
    title: 'Ecommerce - Carrinho'
  },
  {
    path: 'search',
    component: ProductSearchComponent,
    title: 'Ecommerce - Busca'
  },
  {
    path: 'profile',
    component: UserComponent,
    title: 'Ecommerce - Perfil'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
