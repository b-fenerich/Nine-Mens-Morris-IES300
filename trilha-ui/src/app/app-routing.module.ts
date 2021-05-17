import { JogoComponent } from './menu-principal/jogo/jogo.component';
import { PersonalizarComponent } from './menu-principal/personalizar/personalizar.component';
import { LojaComponent } from './menu-principal/loja/loja.component';
import { EstatisticaComponent } from './menu-principal/estatistica/estatistica.component';
import { MenuPrincipalComponent } from './menu-principal/menu-principal.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: 'menu', component: MenuPrincipalComponent, pathMatch: 'full'},
  { path: 'estatistica', component: EstatisticaComponent, pathMatch: 'full'},
  { path: 'loja', component: LojaComponent, pathMatch: 'full'},
  { path: 'personalizar', component: PersonalizarComponent, pathMatch: 'full'},
  { path: 'jogo', component: JogoComponent, pathMatch: 'full'},

  { path: 'login', component: LoginPageComponent, pathMatch: 'full' },
  { path: 'cadastro', component: LoginPageComponent, pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
