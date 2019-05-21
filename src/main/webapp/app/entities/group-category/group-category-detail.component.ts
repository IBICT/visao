import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupCategory } from 'app/shared/model/group-category.model';

@Component({
    selector: 'jhi-group-category-detail',
    templateUrl: './group-category-detail.component.html'
})
export class GroupCategoryDetailComponent implements OnInit {
    groupCategory: IGroupCategory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ groupCategory }) => {
            this.groupCategory = groupCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
