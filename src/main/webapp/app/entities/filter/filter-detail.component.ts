import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFilter } from 'app/shared/model/filter.model';

@Component({
    selector: 'jhi-filter-detail',
    templateUrl: './filter-detail.component.html'
})
export class FilterDetailComponent implements OnInit {
    filter: IFilter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filter }) => {
            this.filter = filter;
        });
    }

    previousState() {
        window.history.back();
    }
}
