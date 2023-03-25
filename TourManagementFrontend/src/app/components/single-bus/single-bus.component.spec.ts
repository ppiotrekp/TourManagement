import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleBusComponent } from './single-bus.component';

describe('SingleBusComponent', () => {
  let component: SingleBusComponent;
  let fixture: ComponentFixture<SingleBusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleBusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleBusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
