import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileDestinationViewComponent } from './user-profile-destination-view.component';

describe('UserProfileDestinationViewComponent', () => {
  let component: UserProfileDestinationViewComponent;
  let fixture: ComponentFixture<UserProfileDestinationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserProfileDestinationViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserProfileDestinationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
