import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';

@Component({
    selector: 'jhi-geographic-filter-detail',
    templateUrl: './geographic-filter-detail.component.html'
})
export class GeographicFilterDetailComponent implements OnInit {
    geographicFilter: IGeographicFilter;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ geographicFilter }) => {
            this.geographicFilter = geographicFilter;
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
