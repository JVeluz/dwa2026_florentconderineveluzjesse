import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Player } from '../../../models/player';

@Injectable({
  providedIn: 'root',
})

//Correspond à 'backend\src\main\java\m1\dwa\cv\controllers\UserHttpJavalin.java'
export class RankingService {
  private http = inject(HttpClient);

  getUsers(){
    return this.http.get<Player[]>('/api/users');
  }
}
