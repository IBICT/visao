import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { GroupCategory } from 'app/shared/model/group-category.model';
import { GroupCategoryService } from './group-category.service';
import { GroupCategoryComponent } from './group-category.component';
import { GroupCategoryDetailComponent } from './group-category-detail.component';
import { GroupCategoryUpdateComponent } from './group-category-update.component';
import { GroupCategoryDeletePopupComponent } from './group-category-delete-dialog.component';
import { IGroupCategory } from 'app/shared/model/group-category.model';

@Injectable({ providedIn: 'root' })
export class GroupCategoryResolve implements Resolve<IGroupCategory> {
    constructor(private service: GroupCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((groupCategory: HttpResponse<GroupCategory>) => groupCategory.body));
        }
        return of(new GroupCategory());
    }
}

export const groupCategoryRoute: Routes = [
    {
        path: 'group-category',
        component: GroupCategoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'visaoApp.groupCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-category/:id/view',
        component: GroupCategoryDetailComponent,
        resolve: {
            groupCategory: GroupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.groupCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-category/new',
        component: GroupCategoryUpdateComponent,
        resolve: {
            groupCategory: GroupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.groupCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-category/:id/edit',
        component: GroupCategoryUpdateComponent,
        resolve: {
            groupCategory: GroupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.groupCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupCategoryPopupRoute: Routes = [
    {
        path: 'group-category/:id/delete',
        component: GroupCategoryDeletePopupComponent,
        resolve: {
            groupCategory: GroupCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'visaoApp.groupCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
