import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MetaDado } from 'app/shared/model/meta-dado.model';
import { MetaDadoService } from './meta-dado.service';
import { MetaDadoComponent } from './meta-dado.component';
import { MetaDadoDetailComponent } from './meta-dado-detail.component';
import { MetaDadoUpdateComponent } from './meta-dado-update.component';
import { MetaDadoDeletePopupComponent } from './meta-dado-delete-dialog.component';
import { IMetaDado } from 'app/shared/model/meta-dado.model';

@Injectable({ providedIn: 'root' })
export class MetaDadoResolve implements Resolve<IMetaDado> {
    constructor(private service: MetaDadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((metaDado: HttpResponse<MetaDado>) => metaDado.body));
        }
        return of(new MetaDado());
    }
}

export const metaDadoRoute: Routes = [
    {
        path: 'meta-dado',
        component: MetaDadoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.metaDado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meta-dado/:id/view',
        component: MetaDadoDetailComponent,
        resolve: {
            metaDado: MetaDadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.metaDado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meta-dado/new',
        component: MetaDadoUpdateComponent,
        resolve: {
            metaDado: MetaDadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.metaDado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'meta-dado/:id/edit',
        component: MetaDadoUpdateComponent,
        resolve: {
            metaDado: MetaDadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.metaDado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const metaDadoPopupRoute: Routes = [
    {
        path: 'meta-dado/:id/delete',
        component: MetaDadoDeletePopupComponent,
        resolve: {
            metaDado: MetaDadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.metaDado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
