// *****
// SERVICE APENAS DE AUTENTICAÇÃO
// *****
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CanActivate, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }


  url = environment.apiURL;
  userInfo = undefined
  public currentUser: Observable<any>

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    // Check whether the token is expired and return
    // true or false
    return true;
    // !this.jwtHelper.isTokenExpired(token);
  }

  loginUsuario(user): Observable<any> {
    let credential = {
      password: user.value.password,
      username: user.value.user
    }


    return this.http.post<any>(this.url + '/v1' + '/auth/login', credential)
  }
  _userActionOccured: Subject<void> = new Subject();
  get userActionOccured(): Observable<void> { return this._userActionOccured.asObservable() };

  notifyUserAction() {
    this._userActionOccured.next();
  }



  logoutUser() {
    localStorage.removeItem('token');
    this.router.navigate(['/login'])
  }


}
