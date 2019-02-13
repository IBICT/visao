import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IName } from 'app/shared/model/name.model';

@Component({
    selector: 'jhi-name-detail',
    templateUrl: './name-detail.component.html'
})
export class NameDetailComponent implements OnInit {
    name: IName;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ name }) => {
            this.name = name;
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
