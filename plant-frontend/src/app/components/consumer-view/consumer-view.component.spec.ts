import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsumerViewComponent } from './consumer-view.component';

describe('ConsumerViewComponent', () => {
  let component: ConsumerViewComponent;
  let fixture: ComponentFixture<ConsumerViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsumerViewComponent]
    });
    fixture = TestBed.createComponent(ConsumerViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
