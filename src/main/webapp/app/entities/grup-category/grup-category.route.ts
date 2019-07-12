import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GrupCategory } from 'app/shared/model/grup-category.model';
import { GrupCategoryService } from './grup-category.service';
import { GrupCategoryComponent } from './grup-category.component';
import { GrupCategoryDetailComponent } from './grup-category-detail.component';
import { GrupCategoryUpdateComponent } from './grup-category-update.component';
import { GrupCategoryDeletePopupComponent } from './grup-category-delete-dialog.component';
import { IGrupCategory } from 'app/shared/model/grup-category.model';

@Injectable({ providedIn: 'root' })
export class GrupCategoryResolve implements Resolve<IGrupCategory> {
    constructor(private service: GrupCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((grupCategory: HttpResponse<GrupCategory>) => grupCategory.body));
        }
        return of(new GrupCategory());
    }
}

export const grupCategoryRoute: Routes = [
    {
        path: 'grup-category',
        component: GrupCategoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'GrupCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-category/:id/view',
        component: GrupCategoryDetailComponent,
        resolve: {
            grupCategory: GrupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-category/new',
        component: GrupCategoryUpdateComponent,
        resolve: {
            grupCategory: GrupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupCategories'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grup-category/:id/edit',
        component: GrupCategoryUpdateComponent,
        resolve: {
            grupCategory: GrupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupCategories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grupCategoryPopupRoute: Routes = [
    {
        path: 'grup-category/:id/delete',
        component: GrupCategoryDeletePopupComponent,
        resolve: {
            grupCategory: GrupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrupCategories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
