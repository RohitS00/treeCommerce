import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderPlantsComponent } from './provider-plants.component';

describe('ProviderPlantsComponent', () => {
  let component: ProviderPlantsComponent;
  let fixture: ComponentFixture<ProviderPlantsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProviderPlantsComponent]
    });
    fixture = TestBed.createComponent(ProviderPlantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
