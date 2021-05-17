import { Component, OnInit } from '@angular/core';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { fadeStateTrigger } from '../animations/animation';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [fadeStateTrigger]
})
export class NavbarComponent implements OnInit {

  constructor(public location: Location) { }

  ngOnInit(): void {
  }

  isHomeRouteActivated(): boolean {
    return this.location.path().indexOf('/menu') > -1;
  }
}
