import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFilter } from 'app/shared/model/filter.model';

@Component({
    selector: 'jhi-filter-detail',
    templateUrl: './filter-detail.component.html'
})
export class FilterDetailComponent implements OnInit {
    filter: IFilter;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filter }) => {
            this.filter = filter;
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
