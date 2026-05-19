import { Component, inject } from '@angular/core';
import { GameService } from '../../services/websocket/Game/game';

@Component({
  selector: 'app-clicker',
  imports: [],
  templateUrl: './clicker.html',
  styleUrl: './clicker.scss',
})
export class ClickerComponent {
  private gameService = inject(GameService);
  private clicks = 0;
  onClick(){
    this.clicks++;
  }
  ngOnInit(){
    setInterval(()=>{
      if(this.clicks > 0){
        this.gameService.sendClicks(this.clicks);
        this.clicks = 0;
      }
    }, 1000);
  }
  
}
