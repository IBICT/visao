import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroupCategory } from 'app/shared/model/group-category.model';
import { GroupCategoryService } from './group-category.service';
import { IUser, UserService } from 'app/core';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-group-category-update',
    templateUrl: './group-category-update.component.html'
})
export class GroupCategoryUpdateComponent implements OnInit {
    private _groupCategory: IGroupCategory;
    isSaving: boolean;

    users: IUser[];

    categories: ICategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private groupCategoryService: GroupCategoryService,
        private userService: UserService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupCategory }) => {
            this.groupCategory = groupCategory;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.groupCategoryService.update(this.groupCategory));
        } else {
            this.subscribeToSaveResponse(this.groupCategoryService.create(this.groupCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroupCategory>>) {
        result.subscribe((res: HttpResponse<IGroupCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get groupCategory() {
        return this._groupCategory;
    }

    set groupCategory(groupCategory: IGroupCategory) {
        this._groupCategory = groupCategory;
    }
}
