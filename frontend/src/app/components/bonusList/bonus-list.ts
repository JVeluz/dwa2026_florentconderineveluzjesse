//https://dev.to/eyassh/angular-component-subscription-vs-asyncpipe-use-pipes-when-possible-e9c
import { Component, inject, OnInit } from '@angular/core';
import { Bonus } from '../../models/bonus';
import { BonusService } from '../../services/http/Bonus/bonus';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-bonus-list',
  imports: [
    AsyncPipe
  ],
  templateUrl: './bonus-list.html',
  styleUrl: './bonus-list.scss',
})
export class BonusListComponent{
  private bonusService = inject(BonusService);
  bonuses$ = this.bonusService.getBonuses();
}
