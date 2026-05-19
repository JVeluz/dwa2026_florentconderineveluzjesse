import { Component, inject, Input } from '@angular/core';
import { GameService } from '../../services/websocket/Game/game';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-clicks-info',
  imports: [
    AsyncPipe
  ],
  templateUrl: './clicks-info.html',
  styleUrl: './clicks-info.scss',
})
export class ClicksInfoComponent {
  private gameService = inject(GameService);
  player$ = this.gameService.player$;
  
}
