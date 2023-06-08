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

registerLocaleData(pt);


@NgModule({
  declarations: [AppComponent, UserComponent, AuthComponent, DashboardComponent, ProductDetailComponent, CartComponent, ZipCodePipe],
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
