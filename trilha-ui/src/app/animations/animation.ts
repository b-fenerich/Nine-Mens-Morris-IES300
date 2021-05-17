import { animate, style, transition, trigger, query, stagger } from '@angular/animations';

export const fadeStateTrigger = trigger('fadeState', [
  transition(':enter', [
    style({
      opacity: 0,
      height: '*',
      overflow: 'visible'
    }),
    animate('500ms ease-in')
  ]),
  transition(':leave', animate('500ms ease-out', style({
    opacity: 0,
    height: 0,
    overflow: 'hidden'
  })))
]);

