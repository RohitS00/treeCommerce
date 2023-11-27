import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { PlantsComponent } from './components/plants/plants.component';
import { PlantDetailsComponent } from './components/plant-details/plant-details.component';
import { UploadPlantComponent } from './components/upload-plant/upload-plant.component';

import { ConsumerViewComponent } from './components/consumer-view/consumer-view.component';

import { ProviderPlantsComponent } from './components/provider-plants/provider-plants.component';
import { ProviderViewComponent } from './components/provider-view/provider-view.component';
import { ConsumerOrderComponent } from './components/consumer-order/consumer-order.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'plants', component: PlantsComponent },
  { path: 'plant-details/:id', component: PlantDetailsComponent },
  {path: 'upload-plant', component: UploadPlantComponent},
  { path: 'provider-plants', component: ProviderPlantsComponent },
  { path: 'provider-view', component: ProviderViewComponent },
  { path: 'consumer-view', component: ConsumerViewComponent },
  { path: 'consumer-order', component: ConsumerOrderComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
