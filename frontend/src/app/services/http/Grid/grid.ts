//https://angular.dev/guide/http/setup

import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Pixel } from '../../../models/pixel';
import { Player } from '../../../models/player';

@Injectable({
  providedIn: 'root',
})

//Correspond à 'backend\src\main\java\m1\dwa\cv\controllers\PixelHttpJavalin.java'
export class GridService {
  private http = inject(HttpClient);
  getPixels(){
    return this.http.get<Pixel[]>('/api/pixels');
  }
}
