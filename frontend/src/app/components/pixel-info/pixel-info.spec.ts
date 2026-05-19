import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PixelInfoComponent } from './pixel-info';

describe('PixelInfoComponent', () => {
  let component: PixelInfoComponent;
  let fixture: ComponentFixture<PixelInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PixelInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PixelInfoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
