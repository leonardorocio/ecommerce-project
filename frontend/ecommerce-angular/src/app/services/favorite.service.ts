import { Injectable } from '@angular/core';
import { ErrorHandlingService } from './error-handling.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Favorite } from '../models/favorite';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FavoriteService {
  constructor(
    private errorHandling: ErrorHandlingService,
    private http: HttpClient
  ) {}

  private favoriteBaseURL = 'http://localhost:9000/favorites';

  private options = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getFavorites(): Observable<Favorite[]> {
    return this.http.get<Favorite[]>(this.favoriteBaseURL, this.options).pipe(
      catchError(this.errorHandling.handleError<Favorite[]>('getFavorites', []))
    );
  }

  createFavorite(requestBody: any): Observable<Favorite> {
    return this.http.post<Favorite>(this.favoriteBaseURL, requestBody, this.options).pipe(
      catchError(this.errorHandling.handleError<Favorite>('createFavorite', {} as Favorite))
    );
  }

  deleteFavorite(id: number): Observable<void> {
    return this.http.delete<void>(`${this.favoriteBaseURL}/${id}`, this.options).pipe(
      catchError(this.errorHandling.handleError<void>('deleteFavorite'))
    );
  }

}
