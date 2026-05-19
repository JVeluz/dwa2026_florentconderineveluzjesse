//https://angular.dev/guide/http/setup

import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})

// Correspond à 'backend\src\main\java\m1\dwa\cv\controllers\AccountHttpJavalin.java'
export class AccountService {
  private token: string | null = null;
  private http = inject(HttpClient);

  postLogin(pseudo: string, password: string) {
    return this.http.post('api/login', {pseudo, password});
  }
  postRegister(pseudo: string, password: string) {
    return this.http.post('api/register', {pseudo, password});
  }
  setToken(token: string){
    this.token = token;
  }
  getToken(){
    return this.token;
  }
}
