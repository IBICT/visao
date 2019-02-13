import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MarkerIcon } from 'app/shared/model/marker-icon.model';
import { MarkerIconService } from './marker-icon.service';
import { MarkerIconComponent } from './marker-icon.component';
import { MarkerIconDetailComponent } from './marker-icon-detail.component';
import { MarkerIconUpdateComponent } from './marker-icon-update.component';
import { MarkerIconDeletePopupComponent } from './marker-icon-delete-dialog.component';
import { IMarkerIcon } from 'app/shared/model/marker-icon.model';

@Injectable({ providedIn: 'root' })
export class MarkerIconResolve implements Resolve<IMarkerIcon> {
    constructor(private service: MarkerIconService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((markerIcon: HttpResponse<MarkerIcon>) => markerIcon.body));
        }
        return of(new MarkerIcon());
    }
}

export const markerIconRoute: Routes = [
    {
        path: 'marker-icon',
        component: MarkerIconComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.markerIcon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marker-icon/:id/view',
        component: MarkerIconDetailComponent,
        resolve: {
            markerIcon: MarkerIconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.markerIcon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marker-icon/new',
        component: MarkerIconUpdateComponent,
        resolve: {
            markerIcon: MarkerIconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.markerIcon.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marker-icon/:id/edit',
        component: MarkerIconUpdateComponent,
        resolve: {
            markerIcon: MarkerIconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.markerIcon.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const markerIconPopupRoute: Routes = [
    {
        path: 'marker-icon/:id/delete',
        component: MarkerIconDeletePopupComponent,
        resolve: {
            markerIcon: MarkerIconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.markerIcon.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
