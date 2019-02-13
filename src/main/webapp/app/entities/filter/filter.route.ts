import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Filter } from 'app/shared/model/filter.model';
import { FilterService } from './filter.service';
import { FilterComponent } from './filter.component';
import { FilterDetailComponent } from './filter-detail.component';
import { FilterUpdateComponent } from './filter-update.component';
import { FilterDeletePopupComponent } from './filter-delete-dialog.component';
import { IFilter } from 'app/shared/model/filter.model';

@Injectable({ providedIn: 'root' })
export class FilterResolve implements Resolve<IFilter> {
    constructor(private service: FilterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((filter: HttpResponse<Filter>) => filter.body));
        }
        return of(new Filter());
    }
}

export const filterRoute: Routes = [
    {
        path: 'filter',
        component: FilterComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.filter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filter/:id/view',
        component: FilterDetailComponent,
        resolve: {
            filter: FilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.filter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filter/new',
        component: FilterUpdateComponent,
        resolve: {
            filter: FilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.filter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'filter/:id/edit',
        component: FilterUpdateComponent,
        resolve: {
            filter: FilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.filter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filterPopupRoute: Routes = [
    {
        path: 'filter/:id/delete',
        component: FilterDeletePopupComponent,
        resolve: {
            filter: FilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.filter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
