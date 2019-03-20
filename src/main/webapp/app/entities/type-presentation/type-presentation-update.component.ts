import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITypePresentation } from 'app/shared/model/type-presentation.model';
import { TypePresentationService } from './type-presentation.service';

@Component({
    selector: 'jhi-type-presentation-update',
    templateUrl: './type-presentation-update.component.html'
})
export class TypePresentationUpdateComponent implements OnInit {
    private _typePresentation: ITypePresentation;
    isSaving: boolean;

    constructor(private typePresentationService: TypePresentationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typePresentation }) => {
            this.typePresentation = typePresentation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typePresentation.id !== undefined) {
            this.subscribeToSaveResponse(this.typePresentationService.update(this.typePresentation));
        } else {
            this.subscribeToSaveResponse(this.typePresentationService.create(this.typePresentation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITypePresentation>>) {
        result.subscribe((res: HttpResponse<ITypePresentation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get typePresentation() {
        return this._typePresentation;
    }

    set typePresentation(typePresentation: ITypePresentation) {
        this._typePresentation = typePresentation;
    }
}
