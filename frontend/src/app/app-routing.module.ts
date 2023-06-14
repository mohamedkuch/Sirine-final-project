import { NgModule } from '@angular/core';
import {RouterModule,Routes} from '@angular/router';

import {LoginWindowComponent} from "./views/login-window/login-window.component";
import {RegisterWindowComponent} from "./views/register-window/register-window.component";
import {DatasetWindowComponent} from "./views/dataset-window/dataset-window.component";
import {SingleDataSetComponent} from "./views/dataset-window/single-data-set/single-data-set.component";
import {ProfilWindowComponent} from "./views/profil-window/profil-window.component";
import {DemoDataSetComponent} from "./testing/views/demoDB/demo-data-set/demo-data-set.component";
import {SingleDataViewComponent} from "./testing/views/demoDB/demo-data-set/single-data-view/single-data-view.component"

//testing
import { TestWindowComponent } from './testing/views/test-window/test-window.component';
import {GeoDataMapComponent} from "./views/geodata-map/geo-data-map.component";

const routes: Routes = [
  {path: '', redirectTo:'/login', pathMatch:'full'},
  {path: 'login', component: LoginWindowComponent},
  {path: 'registration', component: RegisterWindowComponent},
  {path: 'datasets', component: DatasetWindowComponent},
  {path: 'datasets/:id', component: SingleDataSetComponent},
  {path: 'profil', component: ProfilWindowComponent},
  {path: 'testWindow', component: TestWindowComponent},
  {path: 'testData', component: DemoDataSetComponent},
  {path: 'testData/:id', component: SingleDataViewComponent},
  {path: 'geoData', component: GeoDataMapComponent}

];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports:[RouterModule]
})
export class AppRoutingModule { }
