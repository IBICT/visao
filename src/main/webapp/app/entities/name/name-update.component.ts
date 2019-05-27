import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IName } from 'app/shared/model/name.model';
import { NameService } from './name.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { ITypePresentation } from 'app/shared/model/type-presentation.model';
import { TypePresentationService } from 'app/entities/type-presentation';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-name-update',
    templateUrl: './name-update.component.html'
})
export class NameUpdateComponent implements OnInit {
    private _name: IName;
    isSaving: boolean;

    categories: ICategory[];

    typepresentations: ITypePresentation[];

    users: IUser[];
    date: string;
    dateChange: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private nameService: NameService,
        private categoryService: CategoryService,
        private typePresentationService: TypePresentationService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ name }) => {
            this.name = name;
        });
        this.categoryService.query().subscribe(
            (res: HttpResponse<ICategory[]>) => {
                this.categories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typePresentationService.query().subscribe(
            (res: HttpResponse<ITypePresentation[]>) => {
                this.typepresentations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.name.date = moment(this.date, DATE_TIME_FORMAT);
        this.name.dateChange = moment(this.dateChange, DATE_TIME_FORMAT);
        if (this.name.id !== undefined) {
            this.subscribeToSaveResponse(this.nameService.update(this.name));
        } else {
            this.subscribeToSaveResponse(this.nameService.create(this.name));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IName>>) {
        result.subscribe((res: HttpResponse<IName>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackTypePresentationById(index: number, item: ITypePresentation) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get name() {
        return this._name;
    }

    set name(name: IName) {
        this._name = name;
        this.date = moment(name.date).format(DATE_TIME_FORMAT);
        this.dateChange = moment(name.dateChange).format(DATE_TIME_FORMAT);
    }
}
