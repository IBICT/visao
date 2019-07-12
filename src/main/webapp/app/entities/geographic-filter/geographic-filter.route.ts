import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GeographicFilter } from 'app/shared/model/geographic-filter.model';
import { GeographicFilterService } from './geographic-filter.service';
import { GeographicFilterComponent } from './geographic-filter.component';
import { GeographicFilterDetailComponent } from './geographic-filter-detail.component';
import { GeographicFilterUpdateComponent } from './geographic-filter-update.component';
import { GeographicFilterDeletePopupComponent } from './geographic-filter-delete-dialog.component';
import { IGeographicFilter } from 'app/shared/model/geographic-filter.model';

@Injectable({ providedIn: 'root' })
export class GeographicFilterResolve implements Resolve<IGeographicFilter> {
    constructor(private service: GeographicFilterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((geographicFilter: HttpResponse<GeographicFilter>) => geographicFilter.body));
        }
        return of(new GeographicFilter());
    }
}

export const geographicFilterRoute: Routes = [
    {
        path: 'geographic-filter',
        component: GeographicFilterComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GeographicFilters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'geographic-filter/:id/view',
        component: GeographicFilterDetailComponent,
        resolve: {
            geographicFilter: GeographicFilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeographicFilters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'geographic-filter/new',
        component: GeographicFilterUpdateComponent,
        resolve: {
            geographicFilter: GeographicFilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeographicFilters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'geographic-filter/:id/edit',
        component: GeographicFilterUpdateComponent,
        resolve: {
            geographicFilter: GeographicFilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeographicFilters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const geographicFilterPopupRoute: Routes = [
    {
        path: 'geographic-filter/:id/delete',
        component: GeographicFilterDeletePopupComponent,
        resolve: {
            geographicFilter: GeographicFilterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GeographicFilters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
