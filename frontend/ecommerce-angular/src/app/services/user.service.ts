import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpClient } from '@angular/common/http';
import { ErrorHandlingService } from './error-handling.service';
import { Observable, Subject, defaultIfEmpty, filter, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // Testando Subject


}
