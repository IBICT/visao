import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-map',
    templateUrl: './map.component.html',
    styleUrls: ['map.component.scss']
})
export class MapComponent implements OnInit {
    message: string;

    constructor() {
        this.message = 'MapComponent message';
    }

    ngOnInit() {}
}
