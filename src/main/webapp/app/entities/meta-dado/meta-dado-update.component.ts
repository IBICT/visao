import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMetaDado } from 'app/shared/model/meta-dado.model';
import { MetaDadoService } from './meta-dado.service';
import { IName } from 'app/shared/model/name.model';
import { NameService } from 'app/entities/name';

@Component({
    selector: 'jhi-meta-dado-update',
    templateUrl: './meta-dado-update.component.html'
})
export class MetaDadoUpdateComponent implements OnInit {
    private _metaDado: IMetaDado;
    isSaving: boolean;

    names: IName[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private metaDadoService: MetaDadoService,
        private nameService: NameService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ metaDado }) => {
            this.metaDado = metaDado;
        });
        this.nameService.query().subscribe(
            (res: HttpResponse<IName[]>) => {
                this.names = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.metaDado.id !== undefined) {
            this.subscribeToSaveResponse(this.metaDadoService.update(this.metaDado));
        } else {
            this.subscribeToSaveResponse(this.metaDadoService.create(this.metaDado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMetaDado>>) {
        result.subscribe((res: HttpResponse<IMetaDado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackNameById(index: number, item: IName) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get metaDado() {
        return this._metaDado;
    }

    set metaDado(metaDado: IMetaDado) {
        this._metaDado = metaDado;
    }
}
