import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClickerComponent } from './clicker';

describe('ClickerComponent', () => {
  let component: ClickerComponent;
  let fixture: ComponentFixture<ClickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClickerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ClickerComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
