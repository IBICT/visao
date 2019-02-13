import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IMarkerIcon } from 'app/shared/model/marker-icon.model';
import { MarkerIconService } from './marker-icon.service';

@Component({
    selector: 'jhi-marker-icon-update',
    templateUrl: './marker-icon-update.component.html'
})
export class MarkerIconUpdateComponent implements OnInit {
    private _markerIcon: IMarkerIcon;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private markerIconService: MarkerIconService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ markerIcon }) => {
            this.markerIcon = markerIcon;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.markerIcon, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.markerIcon.id !== undefined) {
            this.subscribeToSaveResponse(this.markerIconService.update(this.markerIcon));
        } else {
            this.subscribeToSaveResponse(this.markerIconService.create(this.markerIcon));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMarkerIcon>>) {
        result.subscribe((res: HttpResponse<IMarkerIcon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get markerIcon() {
        return this._markerIcon;
    }

    set markerIcon(markerIcon: IMarkerIcon) {
        this._markerIcon = markerIcon;
    }
}
