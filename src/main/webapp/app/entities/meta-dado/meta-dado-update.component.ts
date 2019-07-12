import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMetaDado } from 'app/shared/model/meta-dado.model';
import { MetaDadoService } from './meta-dado.service';
import { IIndicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from 'app/entities/indicator';
import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';
import { GrupIndicatorService } from 'app/entities/grup-indicator';
import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';
import { GeographicFilterService } from 'app/entities/geographic-filter';
import { ILayer } from 'app/shared/model/layer.model';
import { LayerService } from 'app/entities/layer';
import { IGroupLayer } from 'app/shared/model/group-layer.model';
import { GroupLayerService } from 'app/entities/group-layer';

@Component({
    selector: 'jhi-meta-dado-update',
    templateUrl: './meta-dado-update.component.html'
})
export class MetaDadoUpdateComponent implements OnInit {
    private _metaDado: IMetaDado;
    isSaving: boolean;

    indicators: IIndicator[];

    grupindicators: IGrupIndicator[];

    geographicfilters: IGeographicFilter[];

    layers: ILayer[];

    grouplayers: IGroupLayer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private metaDadoService: MetaDadoService,
        private indicatorService: IndicatorService,
        private grupIndicatorService: GrupIndicatorService,
        private geographicFilterService: GeographicFilterService,
        private layerService: LayerService,
        private groupLayerService: GroupLayerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ metaDado }) => {
            this.metaDado = metaDado;
        });
        this.indicatorService.query().subscribe(
            (res: HttpResponse<IIndicator[]>) => {
                this.indicators = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.grupIndicatorService.query().subscribe(
            (res: HttpResponse<IGrupIndicator[]>) => {
                this.grupindicators = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.geographicFilterService.query().subscribe(
            (res: HttpResponse<IGeographicFilter[]>) => {
                this.geographicfilters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.layerService.query().subscribe(
            (res: HttpResponse<ILayer[]>) => {
                this.layers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.groupLayerService.query().subscribe(
            (res: HttpResponse<IGroupLayer[]>) => {
                this.grouplayers = res.body;
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

    trackIndicatorById(index: number, item: IIndicator) {
        return item.id;
    }

    trackGrupIndicatorById(index: number, item: IGrupIndicator) {
        return item.id;
    }

    trackGeographicFilterById(index: number, item: IGeographicFilter) {
        return item.id;
    }

    trackLayerById(index: number, item: ILayer) {
        return item.id;
    }

    trackGroupLayerById(index: number, item: IGroupLayer) {
        return item.id;
    }
    get metaDado() {
        return this._metaDado;
    }

    set metaDado(metaDado: IMetaDado) {
        this._metaDado = metaDado;
    }
}
