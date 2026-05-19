import { inject, Injectable } from '@angular/core';
import { WebSocketService } from '../Websocket/websocket';
import { Player } from '../../../models/player';
import { Pixel } from '../../../models/pixel';
import { PlayerService } from '../../http/Player/player';
import { RankingService } from '../../http/ranking/ranking';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

//Correspond à 'backend\src\main\java\m1\dwa\cv\controllers\PlayerSocketInJavalin.java'
export class GameService {
  private websocket = inject(WebSocketService);
  private rankingService = inject(RankingService);

  player$ = new BehaviorSubject<Player | null >(null);
  players$ = new BehaviorSubject<Player[]>([]);
  pixels$ = new BehaviorSubject<Pixel[]>([]);
  selectedPixel$ = new BehaviorSubject<Pixel | null >(null);

  actionFail: string | null = null;
  onInit(){
    this.rankingService.getUsers().subscribe(players => {
      this.players$.next(players);
    });
    this.websocket.getMessages().subscribe((message) =>{
      if(Array.isArray(message)){
        this.pixels$.next(message);
        console.log("grid loaded.")
        console.log(this.pixels$.value);
      } else if(message.error){
        console.log(message.error);
      } else if(message.message === 'pong'){
        // Do nothing
      } else {
        switch(message.event){
        case 'player-updated':
          this.onPlayerUpdate(message.player);
        break;
        case 'pixels-updated':
          this.onPixelUpdate(message.pixels);
        break;
        case 'action-failed':
          this.onActionFailed(message.message);
        break;
        case 'bonus-started':
          this.onBonusStarted(message.bonusId);
        break;
        case 'bonus-ended':
          this.onBonusEnded(message.bonusId);
        break;
        default:
          console.log("unknown event");
          break;
      }
      }
      
    });
    setInterval(() => {
      this.ping();
    }, 10000);
  }
  joinGame(userId: number, token: string){
    this.websocket.sendMessage({event: 'join-game', userId, token});
  }
  leaveGame(userId: number){
    this.websocket.sendMessage({event: 'leave-game', userId});
  }
  sendClicks(clicks: number, ){
    this.websocket.sendMessage({event: 'clicks-action', clicks});
  }
  purchasePixel(pixels: Pixel[], colorHexadecimal: string){
    const pixelsX = pixels.map(pixel => pixel.x);
    const pixelsY = pixels.map(pixel => pixel.y);
    this.websocket.sendMessage({event: 'paint-pixels-action', pixelsX, pixelsY, colorHexadecimal});
  }
  useBonus(bonusId: number){
    this.websocket.sendMessage({event: 'use-bonus', bonusId});
  }
  ping(){
    this.websocket.sendMessage({event: 'ping'});
  }
  onPlayerUpdate(player : Partial<Player>){
    const current = this.player$.value;

  if (!current) {
    this.player$.next(player as Player);
    return;
  }

  this.player$.next({
    ...current,
    ...player
  });
  }
  onPixelUpdate(newPixels: Pixel[]){
    const current = this.pixels$.value;

    const updated = current.map(pixel => {
      const match = newPixels.find(newPixel => newPixel.id === pixel.id);
      return match ??  pixel;
    })
    this.pixels$.next(updated);
  }
  onActionFailed(message: string){
    this.actionFail = message;
  }
  onBonusStarted(bonusId: number){
    const player = this.player$.value;
    if (!player) return;

    this.player$.next({
      ...player,
      activeBonuses: [
        ...(player.activeBonuses ?? []),
        bonusId
      ]
    });
  }
  onBonusEnded(bonusId: number){
    const player = this.player$.value;
    if (!player) return;

    this.player$.next({
      ...player,
      activeBonuses: (player.activeBonuses ?? []).filter(id => id !== bonusId)
    });
  }
}
