import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GridService } from './services/http/Grid/grid';
import { GameService } from './services/websocket/Game/game';
import { BonusListComponent } from './components/bonusList/bonus-list';
import { Account } from './components/account/account';
import { GridComponent } from './components/grid/grid';
import { ClickerComponent } from './components/clicker/clicker';
import { ClicksInfoComponent } from './components/clicks-info/clicks-info';
import { PixelInfoComponent } from './components/pixel-info/pixel-info';
import { Ranking } from './components/ranking/ranking';

@Component({
  selector: 'app-root',
  imports: [
    BonusListComponent,
    Account,
    GridComponent,
    ClickerComponent,
    ClicksInfoComponent,
    PixelInfoComponent,
    Ranking
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('Clicker');
  private gameSocket = inject(GameService);
  ngOnInit(){
    this.gameSocket.onInit();
  }
  
}
