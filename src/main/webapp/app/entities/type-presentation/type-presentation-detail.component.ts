import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITypePresentation } from 'app/shared/model/type-presentation.model';

@Component({
    selector: 'jhi-type-presentation-detail',
    templateUrl: './type-presentation-detail.component.html'
})
export class TypePresentationDetailComponent implements OnInit {
    typePresentation: ITypePresentation;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typePresentation }) => {
            this.typePresentation = typePresentation;
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
