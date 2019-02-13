import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Indicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';
import { IndicatorComponent } from './indicator.component';
import { IndicatorDetailComponent } from './indicator-detail.component';
import { IndicatorUpdateComponent } from './indicator-update.component';
import { IndicatorDeletePopupComponent } from './indicator-delete-dialog.component';
import { IIndicator } from 'app/shared/model/indicator.model';

@Injectable({ providedIn: 'root' })
export class IndicatorResolve implements Resolve<IIndicator> {
    constructor(private service: IndicatorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((indicator: HttpResponse<Indicator>) => indicator.body));
        }
        return of(new Indicator());
    }
}

export const indicatorRoute: Routes = [
    {
        path: 'indicator',
        component: IndicatorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.indicator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indicator/:id/view',
        component: IndicatorDetailComponent,
        resolve: {
            indicator: IndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.indicator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indicator/new',
        component: IndicatorUpdateComponent,
        resolve: {
            indicator: IndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.indicator.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indicator/:id/edit',
        component: IndicatorUpdateComponent,
        resolve: {
            indicator: IndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.indicator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const indicatorPopupRoute: Routes = [
    {
        path: 'indicator/:id/delete',
        component: IndicatorDeletePopupComponent,
        resolve: {
            indicator: IndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.indicator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
