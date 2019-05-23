import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IChart } from 'app/shared/model/chart.model';

@Component({
    selector: 'jhi-chart-detail',
    templateUrl: './chart-detail.component.html'
})
export class ChartDetailComponent implements OnInit {
    chart: IChart;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chart }) => {
            this.chart = chart;
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
