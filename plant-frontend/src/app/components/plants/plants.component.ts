import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Plant } from 'src/app/utils/modals/Plant';
import { PlantService } from 'src/app/utils/services/plant.service';

@Component({
  selector: 'app-plants',
  templateUrl: './plants.component.html',
  styleUrls: ['./plants.component.css']
})
export class PlantsComponent {
  plants: Plant[] = [];

  constructor(private plantService: PlantService, private router: Router) { }

  ngOnInit(): void {
    this.loadPlants();
  }

  loadPlants(): void {
    this.plantService.getAllPlants()
      .subscribe(
        data => {
          this.plants = data;
        },
        error => {
          console.error(error);
        }
      );
  }

  getPlantImageUrl(base64String: string): string {
    return 'data:image/jpeg;base64,' + base64String;
  }

  viewPlantDetails(plantId: number): void {
    this.router.navigate(['/plant-details', plantId]);
  }
}
