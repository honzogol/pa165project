import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeaponCreateComponent } from './weapon-create.component';

describe('WeaponCreateComponent', () => {
  let component: WeaponCreateComponent;
  let fixture: ComponentFixture<WeaponCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeaponCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeaponCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
