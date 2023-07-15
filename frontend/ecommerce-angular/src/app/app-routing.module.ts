import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { UserComponent } from './components/user/user.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { AdminComponent } from './components/admin/admin.component';
import { isAdminGuard } from './routes/is-admin.guard';
import { AdminEntityComponent } from './components/admin-entity/admin-entity.component';
import { AdminInputComponent } from './components/admin-input/admin-input.component';
import { isLoggedGuard } from './routes/is-logged.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'auth',
    component: AuthComponent,
    title: 'Ecommerce - Autenticação',
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    title: 'Ecommerce - Início',
  },
  {
    path: 'produto/:id',
    component: ProductDetailComponent,
    title: 'Ecommerce - Produto',
  },
  {
    path: 'carrinho',
    component: CartComponent,
    title: 'Ecommerce - Carrinho',
    canActivate: [isLoggedGuard],
  },
  {
    path: 'search',
    component: ProductSearchComponent,
    title: 'Ecommerce - Busca',
  },
  {
    path: 'profile',
    component: UserComponent,
    title: 'Ecommerce - Perfil',
    canActivate: [isLoggedGuard]
  },
  {
    path: 'admin',
    component: AdminComponent,
    title: 'Ecommerce - Admin',
    canActivate: [isAdminGuard],
  },
  {
    path: 'admin/:type',
    component: AdminEntityComponent,
    canActivate: [isAdminGuard],
  },
  {
    path: 'admin/:type/:method',
    component: AdminInputComponent,
    canActivate: [isAdminGuard],
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      onSameUrlNavigation: 'reload',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
