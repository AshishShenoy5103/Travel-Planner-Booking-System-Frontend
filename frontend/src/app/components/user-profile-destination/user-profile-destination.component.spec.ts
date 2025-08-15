import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileDestinationComponent } from './user-profile-destination.component';

describe('UserProfileDestinationComponent', () => {
  let component: UserProfileDestinationComponent;
  let fixture: ComponentFixture<UserProfileDestinationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserProfileDestinationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserProfileDestinationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
