import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIndicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';
import { IName } from 'app/shared/model/name.model';
import { NameService } from 'app/entities/name';
import { IYear } from 'app/shared/model/year.model';
import { YearService } from 'app/entities/year';

@Component({
    selector: 'jhi-indicator-update',
    templateUrl: './indicator-update.component.html'
})
export class IndicatorUpdateComponent implements OnInit {
    private _indicator: IIndicator;
    isSaving: boolean;

    regions: IRegion[];

    names: IName[];

    years: IYear[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private indicatorService: IndicatorService,
        private regionService: RegionService,
        private nameService: NameService,
        private yearService: YearService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ indicator }) => {
            this.indicator = indicator;
        });
        this.regionService.query().subscribe(
            (res: HttpResponse<IRegion[]>) => {
                this.regions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.nameService.query().subscribe(
            (res: HttpResponse<IName[]>) => {
                this.names = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.yearService.query().subscribe(
            (res: HttpResponse<IYear[]>) => {
                this.years = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.indicator.id !== undefined) {
            this.subscribeToSaveResponse(this.indicatorService.update(this.indicator));
        } else {
            this.subscribeToSaveResponse(this.indicatorService.create(this.indicator));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIndicator>>) {
        result.subscribe((res: HttpResponse<IIndicator>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRegionById(index: number, item: IRegion) {
        return item.id;
    }

    trackNameById(index: number, item: IName) {
        return item.id;
    }

    trackYearById(index: number, item: IYear) {
        return item.id;
    }
    get indicator() {
        return this._indicator;
    }

    set indicator(indicator: IIndicator) {
        this._indicator = indicator;
    }
}
