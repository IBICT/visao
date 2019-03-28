import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { latLng, tileLayer } from 'leaflet';
import { MapService } from './map.service';
import { Principal } from 'app/core';
import { Subscription } from 'rxjs/Rx';

@Component({
    selector: 'jhi-map',
    templateUrl: './map.component.html',
    styleUrls: ['map.component.scss']
})
export class MapComponent implements OnInit, OnDestroy {
    currentAccount: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    // Define our base layers so we can reference them multiple times
    OSMaps = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        // attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        maxZoom: 20,
        detectRetina: true
    });

    // Layers
    layersControl = {
        baseLayers: {
            'OS Maps': this.OSMaps
        }
    };

    // Set the initial set
    options = {
        // layers: [this.OSMaps, this.trace, this.center],
        layers: [this.OSMaps],
        zoom: 4,
        center: latLng([-15, -50])
    };
    constructor(
        private mapService: MapService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {}

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
