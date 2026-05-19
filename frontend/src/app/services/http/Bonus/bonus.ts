import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Bonus } from '../../../models/bonus';

@Injectable({
  providedIn: 'root',
})
export class BonusService {
  private http = inject(HttpClient);

  getBonuses(){
    return this.http.get<Bonus[]>('/api/bonuses');
  }
}
