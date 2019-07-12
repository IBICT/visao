import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Chart } from 'app/shared/model/chart.model';
import { ChartService } from './chart.service';
import { ChartComponent } from './chart.component';
import { ChartDetailComponent } from './chart-detail.component';
import { ChartUpdateComponent } from './chart-update.component';
import { ChartDeletePopupComponent } from './chart-delete-dialog.component';
import { IChart } from 'app/shared/model/chart.model';

@Injectable({ providedIn: 'root' })
export class ChartResolve implements Resolve<IChart> {
    constructor(private service: ChartService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((chart: HttpResponse<Chart>) => chart.body));
        }
        return of(new Chart());
    }
}

export const chartRoute: Routes = [
    {
        path: 'chart',
        component: ChartComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Charts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chart/:id/view',
        component: ChartDetailComponent,
        resolve: {
            chart: ChartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Charts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chart/new',
        component: ChartUpdateComponent,
        resolve: {
            chart: ChartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Charts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chart/:id/edit',
        component: ChartUpdateComponent,
        resolve: {
            chart: ChartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Charts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chartPopupRoute: Routes = [
    {
        path: 'chart/:id/delete',
        component: ChartDeletePopupComponent,
        resolve: {
            chart: ChartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Charts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
