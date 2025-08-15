import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserProfileDestinationViewLayoutComponent } from './user-profile-destination-view-layout.component';

describe('UserProfileDestinationViewLayoutComponent', () => {
  let component: UserProfileDestinationViewLayoutComponent;
  let fixture: ComponentFixture<UserProfileDestinationViewLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserProfileDestinationViewLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserProfileDestinationViewLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
