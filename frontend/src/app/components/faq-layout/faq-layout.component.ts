import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { FaqComponent } from "../faq/faq.component";

@Component({
  selector: 'app-faq-layout',
  imports: [HeaderComponent, FooterComponent, FaqComponent],
  templateUrl: './faq-layout.component.html',
  styleUrl: './faq-layout.component.css'
})
export class FaqLayoutComponent {

}
