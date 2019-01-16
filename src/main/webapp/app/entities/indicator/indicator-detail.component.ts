import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicator } from 'app/shared/model/indicator.model';

@Component({
    selector: 'jhi-indicator-detail',
    templateUrl: './indicator-detail.component.html'
})
export class IndicatorDetailComponent implements OnInit {
    indicator: IIndicator;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ indicator }) => {
            this.indicator = indicator;
        });
    }

    previousState() {
        window.history.back();
    }
}
