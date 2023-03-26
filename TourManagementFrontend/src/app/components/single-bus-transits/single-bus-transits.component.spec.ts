import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleBusTransitsComponent } from './single-bus-transits.component';

describe('SingleBusTransitsComponent', () => {
  let component: SingleBusTransitsComponent;
  let fixture: ComponentFixture<SingleBusTransitsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleBusTransitsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleBusTransitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
