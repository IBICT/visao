import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GroupLayer } from 'app/shared/model/group-layer.model';
import { GroupLayerService } from './group-layer.service';
import { GroupLayerComponent } from './group-layer.component';
import { GroupLayerDetailComponent } from './group-layer-detail.component';
import { GroupLayerUpdateComponent } from './group-layer-update.component';
import { GroupLayerDeletePopupComponent } from './group-layer-delete-dialog.component';
import { IGroupLayer } from 'app/shared/model/group-layer.model';

@Injectable({ providedIn: 'root' })
export class GroupLayerResolve implements Resolve<IGroupLayer> {
    constructor(private service: GroupLayerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((groupLayer: HttpResponse<GroupLayer>) => groupLayer.body));
        }
        return of(new GroupLayer());
    }
}

export const groupLayerRoute: Routes = [
    {
        path: 'group-layer',
        component: GroupLayerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GroupLayers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-layer/:id/view',
        component: GroupLayerDetailComponent,
        resolve: {
            groupLayer: GroupLayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroupLayers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-layer/new',
        component: GroupLayerUpdateComponent,
        resolve: {
            groupLayer: GroupLayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroupLayers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-layer/:id/edit',
        component: GroupLayerUpdateComponent,
        resolve: {
            groupLayer: GroupLayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroupLayers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupLayerPopupRoute: Routes = [
    {
        path: 'group-layer/:id/delete',
        component: GroupLayerDeletePopupComponent,
        resolve: {
            groupLayer: GroupLayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GroupLayers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
