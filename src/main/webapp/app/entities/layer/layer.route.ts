import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Layer } from 'app/shared/model/layer.model';
import { LayerService } from './layer.service';
import { LayerComponent } from './layer.component';
import { LayerDetailComponent } from './layer-detail.component';
import { LayerUpdateComponent } from './layer-update.component';
import { LayerDeletePopupComponent } from './layer-delete-dialog.component';
import { ILayer } from 'app/shared/model/layer.model';

@Injectable({ providedIn: 'root' })
export class LayerResolve implements Resolve<ILayer> {
    constructor(private service: LayerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((layer: HttpResponse<Layer>) => layer.body));
        }
        return of(new Layer());
    }
}

export const layerRoute: Routes = [
    {
        path: 'layer',
        component: LayerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'layer/:id/view',
        component: LayerDetailComponent,
        resolve: {
            layer: LayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'layer/new',
        component: LayerUpdateComponent,
        resolve: {
            layer: LayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'layer/:id/edit',
        component: LayerUpdateComponent,
        resolve: {
            layer: LayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const layerPopupRoute: Routes = [
    {
        path: 'layer/:id/delete',
        component: LayerDeletePopupComponent,
        resolve: {
            layer: LayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
