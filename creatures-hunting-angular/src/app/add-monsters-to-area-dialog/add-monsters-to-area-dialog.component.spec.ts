import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMonstersToAreaComponent } from './add-monsters-to-area-dialog.component';

describe('AddMonstersToAreaComponent', () => {
  let component: AddMonstersToAreaComponent;
  let fixture: ComponentFixture<AddMonstersToAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMonstersToAreaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMonstersToAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
