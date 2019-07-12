import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroupLayer } from 'app/shared/model/group-layer.model';
import { GroupLayerService } from './group-layer.service';
import { IUser, UserService } from 'app/core';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
    selector: 'jhi-group-layer-update',
    templateUrl: './group-layer-update.component.html'
})
export class GroupLayerUpdateComponent implements OnInit {
    private _groupLayer: IGroupLayer;
    isSaving: boolean;

    users: IUser[];

    categories: ICategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private groupLayerService: GroupLayerService,
        private userService: UserService,
        private categoryService: CategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupLayer }) => {
            this.groupLayer = groupLayer;
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
        if (this.groupLayer.id !== undefined) {
            this.subscribeToSaveResponse(this.groupLayerService.update(this.groupLayer));
        } else {
            this.subscribeToSaveResponse(this.groupLayerService.create(this.groupLayer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroupLayer>>) {
        result.subscribe((res: HttpResponse<IGroupLayer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get groupLayer() {
        return this._groupLayer;
    }

    set groupLayer(groupLayer: IGroupLayer) {
        this._groupLayer = groupLayer;
    }
}
