import { Component, OnInit } from '@angular/core';
import { Plant } from 'src/app/utils/modals/Plant';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { UserService } from 'src/app/utils/services/user.service';
;

@Component({
  selector: 'app-provider-plants',
  templateUrl: './provider-plants.component.html',
  styleUrls: ['./provider-plants.component.css']
})
export class ProviderPlantsComponent implements OnInit {
  plants: Plant[] = [];

  constructor(private userService: UserService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    const userId = this.authenticationService.getUserId();
    this.userService.providerPlants(userId).subscribe(
      plants => {
        this.plants = plants;
      },
      error => {
        console.error(error);
      }
    );
  }

}
