import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialologyComponent } from './dialology.component';

describe('DialologyComponent', () => {
  let component: DialologyComponent;
  let fixture: ComponentFixture<DialologyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialologyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialologyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
