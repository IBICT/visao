import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';
import { GrupIndicatorService } from './grup-indicator.service';
import { IUser, UserService } from 'app/core';
import { ITypePresentation } from 'app/shared/model/type-presentation.model';
import { TypePresentationService } from 'app/entities/type-presentation';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-grup-indicator-update',
    templateUrl: './grup-indicator-update.component.html'
})
export class GrupIndicatorUpdateComponent implements OnInit {
    private _grupIndicator: IGrupIndicator;
    isSaving: boolean;

    users: IUser[];

    typepresentations: ITypePresentation[];

    categories: ICategory[];
    date: string;
    dateChange: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private grupIndicatorService: GrupIndicatorService,
        private userService: UserService,
        private typePresentationService: TypePresentationService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grupIndicator }) => {
            this.grupIndicator = grupIndicator;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typePresentationService.query().subscribe(
            (res: HttpResponse<ITypePresentation[]>) => {
                this.typepresentations = res.body;
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
        this.grupIndicator.date = moment(this.date, DATE_TIME_FORMAT);
        this.grupIndicator.dateChange = moment(this.dateChange, DATE_TIME_FORMAT);
        if (this.grupIndicator.id !== undefined) {
            this.subscribeToSaveResponse(this.grupIndicatorService.update(this.grupIndicator));
        } else {
            this.subscribeToSaveResponse(this.grupIndicatorService.create(this.grupIndicator));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGrupIndicator>>) {
        result.subscribe((res: HttpResponse<IGrupIndicator>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypePresentationById(index: number, item: ITypePresentation) {
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
    get grupIndicator() {
        return this._grupIndicator;
    }

    set grupIndicator(grupIndicator: IGrupIndicator) {
        this._grupIndicator = grupIndicator;
        this.date = moment(grupIndicator.date).format(DATE_TIME_FORMAT);
        this.dateChange = moment(grupIndicator.dateChange).format(DATE_TIME_FORMAT);
    }
}
