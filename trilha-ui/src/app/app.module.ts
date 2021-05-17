import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { LoginFormComponent } from './login-page/login-form/login-form.component';
import { CadastroFormComponent } from './login-page/cadastro-form/cadastro-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MaterialModule } from './material.module';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LojaComponent } from './menu-principal/loja/loja.component';
import { EstatisticaComponent } from './menu-principal/estatistica/estatistica.component';
import { PersonalizarComponent } from './menu-principal/personalizar/personalizar.component';
import { JogoComponent } from './menu-principal/jogo/jogo.component';
import { NavbarComponent } from './navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    LoginFormComponent,
    CadastroFormComponent,
    MenuPrincipalComponent,
    LojaComponent,
    EstatisticaComponent,
    PersonalizarComponent,
    JogoComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    MatIconModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
