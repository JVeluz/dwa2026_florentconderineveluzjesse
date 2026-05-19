import { Component, inject } from '@angular/core';
import { GridService } from '../../services/http/Grid/grid';
import { AsyncPipe, NgClass } from '@angular/common';
import { Pixel } from '../../models/pixel';
import { RankingService } from '../../services/http/ranking/ranking';
import { GameService } from '../../services/websocket/Game/game';
import { Player } from '../../models/player';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  selector: 'app-grid',
  imports: [
    AsyncPipe,
    FormsModule
  ],
  templateUrl: './grid.html',
  styleUrl: './grid.scss',
})
export class GridComponent {

  private grid = inject(GridService);
  private gameService = inject(GameService);
  selectedPixel: Pixel | null = null;
  selectedColor: string = '#FFFFFF';
  pixels$ = this.gameService.pixels$;
  player$ = this.gameService.player$;
  onClick(pixel: Pixel){
    if (this.selectedPixel?.id === pixel.id) {
      this.selectedPixel = null;
    } else {
      this.selectedPixel = pixel;
    }
  }
  purchasePixel(pixels: Pixel[], selectedColor: string){

    if(!this.selectedPixel) return;

    this.gameService.purchasePixel(pixels, selectedColor);
  }
  trackByPixel(index: number, pixel: Pixel) {
    return pixel.id;
  }
  isPlayerConnected() {
    return this.gameService.player$.value != null;
  }
  canBePurchased() {
  if (!this.gameService.player$.value) return false;
  if (!this.selectedPixel) return false;
  return this.selectedPixel.ownerId !== this.gameService.player$.value.id;
  }
  getOwnerPseudo(ownerId: number) {
  return this.gameService.players$.value
    .find(p => p.id === ownerId)
    ?.pseudo ?? 'No owner';
  }
}
