import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { AuthComponent } from './components/auth/auth.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthInterceptorService } from './services/auth-interceptor.service';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { registerLocaleData } from '@angular/common';
import pt from '@angular/common/locales/pt';
import { CartComponent } from './components/cart/cart.component';
import { ZipCodePipe } from './pipes/zip-code.pipe';
import { FooterComponent } from './components/footer/footer.component';
import { NavComponent } from './components/nav/nav.component';
import { AddressComponent } from './components/address/address.component';
import { PaginatorComponent } from './components/paginator/paginator.component';
import { CartProductComponent } from './components/cart-product/cart-product.component';
import { ShippingComponent } from './components/shipping/shipping.component';
import { CommentComponent } from './components/comment/comment.component';
import { DropdownComponent } from './components/dropdown/dropdown.component';
import { ProductSearchComponent } from './components/product-search/product-search.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { CartSummaryComponent } from './components/cart-summary/cart-summary.component';
import { AdminComponent } from './components/admin/admin.component';
import { AdminEntityComponent } from './components/admin-entity/admin-entity.component';
import { AdminInputComponent } from './components/admin-input/admin-input.component';
import { CamelCasePipe } from './pipes/camel-case.pipe';
import { AdminRequestResponseComponent } from './components/admin-request-response/admin-request-response.component';

registerLocaleData(pt);


@NgModule({
  declarations: [AppComponent, UserComponent, AuthComponent, DashboardComponent, ProductDetailComponent, CartComponent, ZipCodePipe, FooterComponent, NavComponent, AddressComponent, PaginatorComponent, CartProductComponent, ShippingComponent, CommentComponent, DropdownComponent, ProductSearchComponent, ProductCardComponent, CartSummaryComponent, AdminComponent, AdminEntityComponent, AdminInputComponent, CamelCasePipe, AdminRequestResponseComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-right',
      preventDuplicates: true
    }),
    BrowserAnimationsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true},
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
