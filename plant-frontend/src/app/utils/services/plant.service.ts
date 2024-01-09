import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Plant } from '../modals/Plant';
import { AuthenticationService } from './authentication-service.service';
import { UploadPlant } from '../modals/UploadPlant';

@Injectable({
  providedIn: 'root'
})
export class PlantService {

  private apiUrl = 'http://localhost:8080/'; // Update with your backend API URL

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAllPlants(): Observable<Plant[]> {
    return this.http.get<Plant[]>(`${this.apiUrl}`);
  }
  getPlantById(id: number): Observable<Plant> {
    return this.http.get<Plant>(`${this.apiUrl}plants/${id}`);
  }
  buyPlant(consumerId: number | undefined, plantId: number, quantity: number): Observable<any> {
    const url = `${this.apiUrl}buy-plant/${consumerId}/${plantId}/${quantity}`;
    return this.http.post<string>(url, null);
  }

  addToCart(consumerId: number | undefined, plantId: number, quantity: number): Observable<any> {
    const url = `${this.apiUrl}add-to-cart/${consumerId}/${plantId}/${quantity}`;
    return this.http.post(url, null);
  }
  isUserAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  addPlant(userId: number | undefined,newPlant: UploadPlant): Observable<Plant> {
    return this.http.post<Plant>(`${this.apiUrl}addPlant/${userId}`, newPlant);
  }
  // savePlant(userId: number, formData: FormData): Observable<any> {
  //   const url = `${this.apiUrl}savePlant/${userId}`;
  //   return this.http.post(url, formData);
  // }
  savePlant(userId: number, formData: FormData): Observable<any> {
    const url = `${this.apiUrl}savePlant?Uid=${userId}`;
    return this.http.post(url, formData);
  }
}
