import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleTransitComponent } from './single-transit.component';

describe('SingleTransitComponent', () => {
  let component: SingleTransitComponent;
  let fixture: ComponentFixture<SingleTransitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleTransitComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleTransitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
