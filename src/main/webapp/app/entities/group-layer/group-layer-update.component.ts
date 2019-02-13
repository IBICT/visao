import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroupLayer } from 'app/shared/model/group-layer.model';
import { GroupLayerService } from './group-layer.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-group-layer-update',
    templateUrl: './group-layer-update.component.html'
})
export class GroupLayerUpdateComponent implements OnInit {
    private _groupLayer: IGroupLayer;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private groupLayerService: GroupLayerService,
        private userService: UserService,
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
    get groupLayer() {
        return this._groupLayer;
    }

    set groupLayer(groupLayer: IGroupLayer) {
        this._groupLayer = groupLayer;
    }
}
