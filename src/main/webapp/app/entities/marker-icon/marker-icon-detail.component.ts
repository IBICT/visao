import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMarkerIcon } from 'app/shared/model/marker-icon.model';

@Component({
    selector: 'jhi-marker-icon-detail',
    templateUrl: './marker-icon-detail.component.html'
})
export class MarkerIconDetailComponent implements OnInit {
    markerIcon: IMarkerIcon;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ markerIcon }) => {
            this.markerIcon = markerIcon;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
