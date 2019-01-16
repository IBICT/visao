import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IName } from 'app/shared/model/name.model';
import { NameService } from './name.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-name-update',
    templateUrl: './name-update.component.html'
})
export class NameUpdateComponent implements OnInit {
    private _name: IName;
    isSaving: boolean;

    categories: ICategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private nameService: NameService,
        private categoryService: CategoryService,
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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
    get name() {
        return this._name;
    }

    set name(name: IName) {
        this._name = name;
    }
}
