import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GrupIndicator } from 'app/shared/model/grup-indicator.model';
import { GrupIndicatorService } from './grup-indicator.service';
import { GrupIndicatorComponent } from './grup-indicator.component';
import { GrupIndicatorDetailComponent } from './grup-indicator-detail.component';
import { GrupIndicatorUpdateComponent } from './grup-indicator-update.component';
import { GrupIndicatorDeletePopupComponent } from './grup-indicator-delete-dialog.component';
import { IGrupIndicator } from 'app/shared/model/grup-indicator.model';

@Injectable({ providedIn: 'root' })
export class GrupIndicatorResolve implements Resolve<IGrupIndicator> {
    constructor(private service: GrupIndicatorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((grupIndicator: HttpResponse<GrupIndicator>) => grupIndicator.body));
        }
        return of(new GrupIndicator());
    }
}

export const grupIndicatorRoute: Routes = [
    {
        path: 'grup-indicator',
        component: GrupIndicatorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GrupIndicators'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-indicator/:id/view',
        component: GrupIndicatorDetailComponent,
        resolve: {
            grupIndicator: GrupIndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupIndicators'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-indicator/new',
        component: GrupIndicatorUpdateComponent,
        resolve: {
            grupIndicator: GrupIndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupIndicators'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-indicator/:id/edit',
        component: GrupIndicatorUpdateComponent,
        resolve: {
            grupIndicator: GrupIndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupIndicators'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grupIndicatorPopupRoute: Routes = [
    {
        path: 'grup-indicator/:id/delete',
        component: GrupIndicatorDeletePopupComponent,
        resolve: {
            grupIndicator: GrupIndicatorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupIndicators'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
