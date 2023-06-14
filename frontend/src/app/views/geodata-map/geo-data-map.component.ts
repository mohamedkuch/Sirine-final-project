import {Component, OnInit, AfterViewInit, ViewChild, ElementRef} from '@angular/core';
import * as L from 'leaflet';
import * as jsonData from './knotenpunkte.json';
import { GeoJsonObject } from 'geojson';
import {GeoJSON} from "leaflet";
import {GeoDataMapService} from "./geo-data-map.service";

@Component({
  selector: 'app-geo-data-map',
  templateUrl: './geo-data-map.component.html',
  styleUrls: ['./geo-data-map.component.scss']
})
export class GeoDataMapComponent implements OnInit, AfterViewInit {
  jsonObject!: GeoJsonObject;


  constructor(private geoDataMapService: GeoDataMapService) { }

  ngOnInit(): void {
    this.jsonObject = jsonData as GeoJsonObject;
  }

  ngAfterViewInit(): void {

    //Buttons and other EventListener
    const uploadDialog = document.getElementById("uploadDialog") as HTMLDialogElement;
    const openUploadDialog = document.getElementById("openUploadDialog");
    const closeUploadDialog = document.getElementById("closeUploadDialog");



    openUploadDialog?.addEventListener('click', () => {
        uploadDialog.showModal();
    });

    closeUploadDialog?.addEventListener('click', () => {
        uploadDialog.close();
    });

    // Map

    const map = L.map('map').setView([50.8, 6.1], 12);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    var geojsonFeature: GeoJSON.Feature = {
      "type": "Feature",
      "properties": {
        "name": "Coors Field",
        "amenity": "Baseball Stadium",
        "popupContent": "This is where the Rockies play!"
      },
      "geometry": {
        "type": "Point",
        "coordinates": [6.1, 50.8]
      }
    };

    var myStyle = {
      "color": "#ff7800",
      "weight": 5,
      "opacity": 0.65
    };

    L.geoJSON(geojsonFeature , {style: myStyle}).addTo(map);



    console.log(this.jsonObject)
    L.geoJSON(this.jsonObject).addTo(map);
  }

  @ViewChild('fileInput') fileInput: ElementRef | undefined;
  onSubmit() {
    if (this.fileInput && this.fileInput.nativeElement.files.length > 0) {
      const file = this.fileInput.nativeElement.files[0];
      this.geoDataMapService.uploadGeoData(file);
    }
  }
}
