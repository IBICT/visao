import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYear } from 'app/shared/model/year.model';

@Component({
    selector: 'jhi-year-detail',
    templateUrl: './year-detail.component.html'
})
export class YearDetailComponent implements OnInit {
    year: IYear;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ year }) => {
            this.year = year;
        });
    }

    previousState() {
        window.history.back();
    }
}
