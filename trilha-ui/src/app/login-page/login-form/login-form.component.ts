import { fadeStateTrigger } from 'src/app/animations/animation';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
  animations: [fadeStateTrigger]
})
export class LoginFormComponent implements OnInit {
  loginForm = this.fb.group({
    user: [null, [Validators.required, Validators.email]],
    password: [null, Validators.required],
  });

  constructor(private fb: FormBuilder, public authService: AuthService, private snackBar: MatSnackBar, public router: Router) { }
  loading: boolean = false;

  ngOnInit(): void {
  }

      // Snackbar indicativo com booleano de erro
      openSnackBar(message:string , action?: string, error?:boolean) {
        const snackConfig = new MatSnackBarConfig();
        if (!error) {
          snackConfig.duration = 3000;
          this.snackBar.open(message, action, snackConfig)
        } else {
          snackConfig.panelClass = ['error-class'];
          snackConfig.duration = 3000;
          this.snackBar.open(message, action, snackConfig)
  
        }
      }
  
      onSubmit() {
        this.loading = true;
        if (this.loginForm.status == 'INVALID') {
          this.openSnackBar('Algo está errado, por favor verifique...', 'Fechar', false)
          this.loading=false;
          return
        } else {
          this.authService.loginUsuario(this.loginForm).subscribe(loggingData => {
            this.openSnackBar('Entrando...', 'Fechar', false)
            const token = loggingData.access_token
            localStorage.setItem('token', token);
            const userInfo = loggingData.user_information;
            localStorage.setItem('userInfo', JSON.stringify(userInfo));
            this.loading = false;
            this.router.navigate(['/home'])
          }, err => {
            this.openSnackBar('Usuário ou senha incorretos', 'Fechar', false)
            this.loading = false;
          })
        };
      }
    
}
