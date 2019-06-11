import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IGroupCategory } from 'app/shared/model/group-category.model';

@Component({
    selector: 'jhi-group-category-detail',
    templateUrl: './group-category-detail.component.html'
})
export class GroupCategoryDetailComponent implements OnInit {
    groupCategory: IGroupCategory;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupCategory }) => {
            this.groupCategory = groupCategory;
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
