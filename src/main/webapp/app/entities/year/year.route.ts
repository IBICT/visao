import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Year } from 'app/shared/model/year.model';
import { YearService } from './year.service';
import { YearComponent } from './year.component';
import { YearDetailComponent } from './year-detail.component';
import { YearUpdateComponent } from './year-update.component';
import { YearDeletePopupComponent } from './year-delete-dialog.component';
import { IYear } from 'app/shared/model/year.model';

@Injectable({ providedIn: 'root' })
export class YearResolve implements Resolve<IYear> {
    constructor(private service: YearService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((year: HttpResponse<Year>) => year.body));
        }
        return of(new Year());
    }
}

export const yearRoute: Routes = [
    {
        path: 'year',
        component: YearComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Years'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'year/:id/view',
        component: YearDetailComponent,
        resolve: {
            year: YearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Years'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'year/new',
        component: YearUpdateComponent,
        resolve: {
            year: YearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Years'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'year/:id/edit',
        component: YearUpdateComponent,
        resolve: {
            year: YearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Years'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const yearPopupRoute: Routes = [
    {
        path: 'year/:id/delete',
        component: YearDeletePopupComponent,
        resolve: {
            year: YearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Years'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
