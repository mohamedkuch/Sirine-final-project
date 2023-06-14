import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginWindowComponent } from './views/login-window/login-window.component';
import { RegisterWindowComponent } from './views/register-window/register-window.component';
import { DatasetWindowComponent } from './views/dataset-window/dataset-window.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { AppRoutingModule } from './app-routing.module';
import { SingleDataSetComponent } from './views/dataset-window/single-data-set/single-data-set.component';
import { ProfilWindowComponent } from './views/profil-window/profil-window.component';
import { TestWindowComponent } from './testing/views/test-window/test-window.component';
import { DemoDataSetComponent } from './testing/views/demoDB/demo-data-set/demo-data-set.component';

import { DemoDataSetService } from './testing/views/demoDB/demo-data-set/demo-data-set.service';
import { SingleDataViewComponent } from './testing/views/demoDB/demo-data-set/single-data-view/single-data-view.component';
import { NgOptimizedImage } from '@angular/common';
import { NgJsonEditorModule } from 'ang-jsoneditor';
import { GeoDataMapComponent } from './views/geodata-map/geo-data-map.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginWindowComponent,
    RegisterWindowComponent,
    DatasetWindowComponent,
    ToolbarComponent,
    SingleDataSetComponent,
    ProfilWindowComponent,
    TestWindowComponent,
    DemoDataSetComponent,
    SingleDataViewComponent,
    GeoDataMapComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgOptimizedImage,
    NgJsonEditorModule,
  ],
  providers: [DemoDataSetService],
  bootstrap: [AppComponent],
})
export class AppModule {}
