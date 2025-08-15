import { Component } from '@angular/core';
import { FooterComponent } from "../footer/footer.component";
import { HeaderComponent } from "../header/header.component";
import { UserProfileDestinationViewComponent } from "../user-profile-destination-view/user-profile-destination-view.component";

@Component({
  selector: 'app-user-profile-destination-view-layout',
  imports: [FooterComponent, HeaderComponent, UserProfileDestinationViewComponent],
  templateUrl: './user-profile-destination-view-layout.component.html',
  styleUrl: './user-profile-destination-view-layout.component.css'
})
export class UserProfileDestinationViewLayoutComponent {

}
