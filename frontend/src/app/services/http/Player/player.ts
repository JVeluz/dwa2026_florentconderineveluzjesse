//https://angular.dev/guide/http/setup

import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Player } from '../../../models/player';

@Injectable({
  providedIn: 'root',
})

//Correspond à 'backend\src\main\java\m1\dwa\cv\controllers\PlayerHttpJavalin.java'
export class PlayerService {
  private http = inject(HttpClient);

  postPurchaseBonus(userId: string, bonusId: string){
    return this.http.post('/api/player/purchase-bonus', {userId, bonusId});
  }
}
