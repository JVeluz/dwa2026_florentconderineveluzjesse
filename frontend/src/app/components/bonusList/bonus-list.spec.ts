import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonusListComponent } from './bonus-list';

describe('BonusListComponent', () => {
  let component: BonusListComponent;
  let fixture: ComponentFixture<BonusListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonusListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BonusListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
