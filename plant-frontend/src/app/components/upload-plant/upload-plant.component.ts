// plant.component.ts

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
// import { PlantService } from '../services/plant.service';
// import { AuthService } from '../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PlantService } from 'src/app/utils/services/plant.service';
import { AuthService } from 'src/app/utils/services/auth.service';
import { AuthenticationService } from 'src/app/utils/services/authentication-service.service';

@Component({
  selector: 'app-upload-plant',
  templateUrl: './upload-plant.component.html',
  styleUrls: ['./upload-plant.component.css']
})
export class UploadPlantComponent implements OnInit {
  plantForm: FormGroup;
  imageFile: File | null = null;

  constructor(
    private plantService: PlantService,
    private authService: AuthService,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.plantForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      water: ['', Validators.required],
      temperature: ['', Validators.required],
      onHandValue: ['', Validators.required],
      pictureData: [null, Validators.required]
    });
  }

  ngOnInit(): void {}

  onImageChange(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.imageFile = event.target.files[0];
    }
  }

  // savePlant() {
  //   const userId = this.authenticationService.getUserId();
  //   console.log("here it is",userId)
  //   if (userId) {
  //     const formData = new FormData();
  //     formData.append('plant', JSON.stringify(this.plantForm.value));
      
  //     if (this.imageFile) {
  //       formData.append('image', this.imageFile);
  //     }
  
  //     this.plantService.savePlant(userId, formData).subscribe(
  //       (response) => {
  //         console.log('Plant saved successfully:', response);
  //         // You can redirect to the plant details page or do any other necessary action
  //       },
  //       (error) => {
  //         console.error('Error saving plant:', error);
  //         // Handle the error as needed
  //       }
  //     );
  //   } else {
  //     console.error('User ID not available');
  //     // Handle the situation when the user ID is not available
  //   }
  // }
  savePlant() {
    const userId = this.authenticationService.getUserId();
    console.log("here it is", userId);
    if (userId) {
      const formData = new FormData();
      formData.append('plant', JSON.stringify(this.plantForm.value));

      if (this.imageFile) {
        formData.append('image', this.imageFile);
      }

      this.plantService.savePlant(userId, formData).subscribe(
        (response) => {
          console.log('Plant saved successfully:', response);
          // You can redirect to the plant details page or do any other necessary action
        },
        (error) => {
          console.error('Error saving plant:', error);
          // Handle the error as needed
        }
      );
    } else {
      console.error('User ID not available');
      // Handle the situation when the user ID is not available
    }
  }
}

