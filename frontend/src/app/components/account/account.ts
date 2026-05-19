//https://www.gaetanrouzies.com/angular-forms
//https://angular.fr/lifecycle/changedetector
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { AccountService } from '../../services/http/Account/account';
import { zip } from 'rxjs';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PlayerService } from '../../services/http/Player/player';
import { GameService } from '../../services/websocket/Game/game';

@Component({
  selector: 'app-account',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './account.html',
  styleUrl: './account.scss',
})
export class Account {
  message = '';
  private account = inject(AccountService);
  private cdr = inject(ChangeDetectorRef);
  private gameService = inject(GameService);
  activeTab: 'login' | 'register' = 'login';

  formGroup = new FormGroup({
    pseudo: new FormControl(''),
    password: new FormControl('')
  });

  onLogin(){
    this.account.postLogin(this.formGroup.value.pseudo as string, this.formGroup.value.password as string).subscribe({
      next: (response: any) => {
        this.account.setToken(response.token as string);
        console.log(response.token);
        this.message = "connecté";
        this.cdr.detectChanges();
        this.gameService.onPlayerUpdate(response.user);
        console.log(response.user, response.token);
        console.log(this.account.getToken());
        this.gameService.joinGame(response.user.id, this.account.getToken() as string);
      },
      error: (err) => {
        console.log(err);
        this.message = err.error;
        this.cdr.detectChanges();
      }
    });
  }
  onRegister(){
    this.account.postRegister(this.formGroup.value.pseudo as string, this.formGroup.value.password as string).subscribe({
      next: () => {
        this.message = "compte créé avec succès";
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
        this.message = err.error;
        this.cdr.detectChanges();
      }
    });
  }
  onLoginPage(){
    this.activeTab = 'login';
  }
  onRegisterPage(){
    this.activeTab = 'register';
  }
}
