import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGrupCategory } from 'app/shared/model/grup-category.model';

@Component({
    selector: 'jhi-grup-category-detail',
    templateUrl: './grup-category-detail.component.html'
})
export class GrupCategoryDetailComponent implements OnInit {
    grupCategory: IGrupCategory;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupCategory }) => {
            this.grupCategory = grupCategory;
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
