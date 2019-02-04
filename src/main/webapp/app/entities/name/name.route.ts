import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Name } from 'app/shared/model/name.model';
import { NameService } from './name.service';
import { NameComponent } from './name.component';
import { NameDetailComponent } from './name-detail.component';
import { NameUpdateComponent } from './name-update.component';
import { NameDeletePopupComponent } from './name-delete-dialog.component';
import { IName } from 'app/shared/model/name.model';

@Injectable({ providedIn: 'root' })
export class NameResolve implements Resolve<IName> {
    constructor(private service: NameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((name: HttpResponse<Name>) => name.body));
        }
        return of(new Name());
    }
}

export const nameRoute: Routes = [
    {
        path: 'name',
        component: NameComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.name.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'name/:id/view',
        component: NameDetailComponent,
        resolve: {
            name: NameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.name.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'name/new',
        component: NameUpdateComponent,
        resolve: {
            name: NameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.name.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'name/:id/edit',
        component: NameUpdateComponent,
        resolve: {
            name: NameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.name.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const namePopupRoute: Routes = [
    {
        path: 'name/:id/delete',
        component: NameDeletePopupComponent,
        resolve: {
            name: NameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.name.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
