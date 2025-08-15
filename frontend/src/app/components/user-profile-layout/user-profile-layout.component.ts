import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { UserProfileComponent } from "../user-profile/user-profile.component";
import { UserProfileDestinationComponent } from "../user-profile-destination/user-profile-destination.component";

@Component({
  selector: 'app-user-profile-layout',
  imports: [HeaderComponent, FooterComponent, UserProfileComponent, UserProfileDestinationComponent],
  templateUrl: './user-profile-layout.component.html',
  styleUrl: './user-profile-layout.component.css'
})
export class UserProfileLayoutComponent {

}
