import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';

@Component({
    selector: 'jhi-grup-indicator-detail',
    templateUrl: './grup-indicator-detail.component.html'
})
export class GrupIndicatorDetailComponent implements OnInit {
    grupIndicator: IGrupIndicator;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupIndicator }) => {
            this.grupIndicator = grupIndicator;
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
