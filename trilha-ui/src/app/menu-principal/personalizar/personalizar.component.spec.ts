import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalizarComponent } from './personalizar.component';

describe('PersonalizarComponent', () => {
  let component: PersonalizarComponent;
  let fixture: ComponentFixture<PersonalizarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalizarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalizarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
