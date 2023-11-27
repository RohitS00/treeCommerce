import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Plant } from 'src/app/utils/modals/Plant';
import { UploadPlant } from 'src/app/utils/modals/UploadPlant';
import { AuthService } from 'src/app/utils/services/auth.service';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { PlantService } from 'src/app/utils/services/plant.service';

@Component({
  selector: 'app-upload-plant',
  templateUrl: './upload-plant.component.html',
  styleUrls: ['./upload-plant.component.css']
})
export class UploadPlantComponent {
  newPlant: UploadPlant = {
    name: '',
    description: '',
    price: 0,
    water: '',
    temperature: '',
    pictureData: '',
    onHandValue: 1,
  
  };

  constructor(private plantService: PlantService,
    private authenticationService: AuthenticationService,
    private authService: AuthService,
    private router: Router) { }

  addPlant() {
    const userId = this.authenticationService.getUserId();
    if (this.authService.isLoggedIn()){
    this.plantService.addPlant(userId, this.newPlant).subscribe(
      plant => {
        alert('Plant added successfully');
        console.log('Plant added successfully', plant);
        // Optionally, navigate to a different page after adding the plant
      },
      error => {
        console.error('Error adding plant', error);
      }
    );}
    else{
      this.router.navigate(['/login']);
    }
  }
}

