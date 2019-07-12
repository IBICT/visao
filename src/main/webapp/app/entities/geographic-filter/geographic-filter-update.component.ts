import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';
import { GeographicFilterService } from './geographic-filter.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region';
import { IUser, UserService } from 'app/core';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-geographic-filter-update',
    templateUrl: './geographic-filter-update.component.html'
})
export class GeographicFilterUpdateComponent implements OnInit {
    private _geographicFilter: IGeographicFilter;
    isSaving: boolean;

    cidadepolos: IRegion[];

    users: IUser[];

    regions: IRegion[];

    categories: ICategory[];
    date: string;
    dateChange: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private geographicFilterService: GeographicFilterService,
        private regionService: RegionService,
        private userService: UserService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ geographicFilter }) => {
            this.geographicFilter = geographicFilter;
        });
        this.regionService.query({ filter: 'geographicfilter-is-null' }).subscribe(
            (res: HttpResponse<IRegion[]>) => {
                if (!this.geographicFilter.cidadePolo || !this.geographicFilter.cidadePolo.id) {
                    this.cidadepolos = res.body;
                } else {
                    this.regionService.find(this.geographicFilter.cidadePolo.id).subscribe(
                        (subRes: HttpResponse<IRegion>) => {
                            this.cidadepolos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
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
        this.geographicFilter.date = moment(this.date, DATE_TIME_FORMAT);
        this.geographicFilter.dateChange = moment(this.dateChange, DATE_TIME_FORMAT);
        if (this.geographicFilter.id !== undefined) {
            this.subscribeToSaveResponse(this.geographicFilterService.update(this.geographicFilter));
        } else {
            this.subscribeToSaveResponse(this.geographicFilterService.create(this.geographicFilter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGeographicFilter>>) {
        result.subscribe((res: HttpResponse<IGeographicFilter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
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
    get geographicFilter() {
        return this._geographicFilter;
    }

    set geographicFilter(geographicFilter: IGeographicFilter) {
        this._geographicFilter = geographicFilter;
        this.date = moment(geographicFilter.date).format(DATE_TIME_FORMAT);
        this.dateChange = moment(geographicFilter.dateChange).format(DATE_TIME_FORMAT);
    }
}
