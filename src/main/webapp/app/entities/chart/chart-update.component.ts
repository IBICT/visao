import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IChart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-chart-update',
    templateUrl: './chart-update.component.html'
})
export class ChartUpdateComponent implements OnInit {
    private _chart: IChart;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private chartService: ChartService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chart }) => {
            this.chart = chart;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.chart.id !== undefined) {
            this.subscribeToSaveResponse(this.chartService.update(this.chart));
        } else {
            this.subscribeToSaveResponse(this.chartService.create(this.chart));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChart>>) {
        result.subscribe((res: HttpResponse<IChart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
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
    get chart() {
        return this._chart;
    }

    set chart(chart: IChart) {
        this._chart = chart;
    }
}
