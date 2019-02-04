import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IFilter } from 'app/shared/model/filter.model';
import { FilterService } from './filter.service';
import { IUser, UserService } from 'app/core';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';

@Component({
    selector: 'jhi-filter-update',
    templateUrl: './filter-update.component.html'
})
export class FilterUpdateComponent implements OnInit {
    private _filter: IFilter;
    isSaving: boolean;

    users: IUser[];

    regions: IRegion[];
    date: string;
    dateChange: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private filterService: FilterService,
        private userService: UserService,
        private regionService: RegionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ filter }) => {
            this.filter = filter;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.regionService.query().subscribe(
            (res: HttpResponse<IRegion[]>) => {
                this.regions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.filter.date = moment(this.date, DATE_TIME_FORMAT);
        this.filter.dateChange = moment(this.dateChange, DATE_TIME_FORMAT);
        if (this.filter.id !== undefined) {
            this.subscribeToSaveResponse(this.filterService.update(this.filter));
        } else {
            this.subscribeToSaveResponse(this.filterService.create(this.filter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFilter>>) {
        result.subscribe((res: HttpResponse<IFilter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRegionById(index: number, item: IRegion) {
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
    get filter() {
        return this._filter;
    }

    set filter(filter: IFilter) {
        this._filter = filter;
        this.date = moment(filter.date).format(DATE_TIME_FORMAT);
        this.dateChange = moment(filter.dateChange).format(DATE_TIME_FORMAT);
    }
}
