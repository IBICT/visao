import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePresentation } from 'app/shared/model/type-presentation.model';

@Component({
    selector: 'jhi-type-presentation-detail',
    templateUrl: './type-presentation-detail.component.html'
})
export class TypePresentationDetailComponent implements OnInit {
    typePresentation: ITypePresentation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typePresentation }) => {
            this.typePresentation = typePresentation;
        });
    }

    previousState() {
        window.history.back();
    }
}
