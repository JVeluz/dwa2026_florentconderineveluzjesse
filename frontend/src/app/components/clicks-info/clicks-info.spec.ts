import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClicksInfoComponent } from './clicks-info';

describe('ClicksInfoComponent', () => {
  let component: ClicksInfoComponent;
  let fixture: ComponentFixture<ClicksInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClicksInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ClicksInfoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
