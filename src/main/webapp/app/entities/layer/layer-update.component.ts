import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils } from 'ng-jhipster';

import { ILayer } from 'app/shared/model/layer.model';
import { LayerService } from './layer.service';

@Component({
    selector: 'jhi-layer-update',
    templateUrl: './layer-update.component.html'
})
export class LayerUpdateComponent implements OnInit {
    private _layer: ILayer;
    isSaving: boolean;
    date: string;
    dateChange: string;

    constructor(private dataUtils: JhiDataUtils, private layerService: LayerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ layer }) => {
            this.layer = layer;
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.layer.date = moment(this.date, DATE_TIME_FORMAT);
        this.layer.dateChange = moment(this.dateChange, DATE_TIME_FORMAT);
        if (this.layer.id !== undefined) {
            this.subscribeToSaveResponse(this.layerService.update(this.layer));
        } else {
            this.subscribeToSaveResponse(this.layerService.create(this.layer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILayer>>) {
        result.subscribe((res: HttpResponse<ILayer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get layer() {
        return this._layer;
    }

    set layer(layer: ILayer) {
        this._layer = layer;
        this.date = moment(layer.date).format(DATE_TIME_FORMAT);
        this.dateChange = moment(layer.dateChange).format(DATE_TIME_FORMAT);
    }
}
