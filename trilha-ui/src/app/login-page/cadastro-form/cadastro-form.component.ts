import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { fadeStateTrigger } from 'src/app/animations/animation';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-cadastro-form',
  templateUrl: './cadastro-form.component.html',
  styleUrls: ['./cadastro-form.component.scss'],
  animations: [fadeStateTrigger]
})

export class CadastroFormComponent implements OnInit {
  cadastroForm = this.fb.group({
    email: [null, [Validators.required, Validators.email]],
    password: [null, [Validators.required, Validators.minLength(8)]],
    user: [null, Validators.required],
    passwordCheck: [null, Validators.required]
  });

  loading: boolean = false;

  constructor(private fb: FormBuilder, public route: Router, private snackBar: MatSnackBar, public router: Router,private userService: UsersService) { }

  ngOnInit(): void {
  }

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

  checkPassword(password, samePass) {
    let pass = password;
    let confirmPass = samePass;

    return pass === confirmPass ? null : { notSame: true }

  }

  onSubmit() {
    this.loading = true;
    if (this.cadastroForm.value.password != this.cadastroForm.value.passwordCheck) {
      this.openSnackBar('As senhas são diferentes, por favor confira', 'fechar')
      this.loading = false
      return
    }
    else {
      if (this.cadastroForm.status == 'VALID') {
        this.userService.signupUsuario(this.cadastroForm).subscribe(resp => {
          this.openSnackBar('Você receberá um e-mail para confirmar seu cadastro, obrigado por se cadastrar', 'Fechar')
          this.route.navigate(['/login'])
        }, err => {
          this.openSnackBar('Erro no seu cadastro, usuário já existe ou não está pré-registrado. \nContate o administrador', 'Fechar')
          this.loading = false;
        },
        );
      } else {
        this.openSnackBar('uhu', 'obrigado')
        this.loading = false;
      }
    }

  }

}
