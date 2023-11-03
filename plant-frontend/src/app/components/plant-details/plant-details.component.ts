import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Plant } from 'src/app/utils/modals/Plant';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';
import { PlantService } from 'src/app/utils/services/plant.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-plant-details',
  templateUrl: './plant-details.component.html',
  styleUrls: ['./plant-details.component.css']
})
export class PlantDetailsComponent implements OnInit {
  plant: Plant | undefined;

  constructor(
    private route: ActivatedRoute,
    private plantService: PlantService,
    private authService: AuthenticationService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.getPlantDetails();
  }

  getPlantDetails(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.plantService.getPlantById(id)
      .subscribe(plant => this.plant = plant);
  }

  buyPlant(plantId: number, quantity: number) {
    const userId = this.authService.getUserId();
    if (this.plantService.isUserAuthenticated()){
    // Make API request with the userId and plantId
    this.plantService.buyPlant(userId, plantId, quantity)
      .subscribe(response => {
        console.log('Purchase successful:', response);
        // this.showSuccessMessage(response);
      });}
      else{
        this.router.navigate(['/login']);
      }
  }
  // showSuccessMessage(message: string) {
  //   this.snackBar.open(message, 'Close', {
  //     duration: 3000, // Duration in milliseconds
  //     panelClass: ['success-toast'], // Define a CSS class for the success toast
  // });
  // }
  
  addToCart(plantId: number, quantity: number) {
    const userId = this.authService.getUserId();
  
    // Make API request with the userId and plantId
    if (this.plantService.isUserAuthenticated()){
    this.plantService.addToCart(userId, plantId, quantity)
      .subscribe(response => {
        console.log('added to cart:', response);
      });}
      else{
        this.router.navigate(['/login']);
      }
  }
}
