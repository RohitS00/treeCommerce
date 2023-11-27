import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPlantComponent } from './upload-plant.component';

describe('UploadPlantComponent', () => {
  let component: UploadPlantComponent;
  let fixture: ComponentFixture<UploadPlantComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UploadPlantComponent]
    });
    fixture = TestBed.createComponent(UploadPlantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
