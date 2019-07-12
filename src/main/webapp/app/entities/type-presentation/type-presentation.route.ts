import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TypePresentation } from 'app/shared/model/type-presentation.model';
import { TypePresentationService } from './type-presentation.service';
import { TypePresentationComponent } from './type-presentation.component';
import { TypePresentationDetailComponent } from './type-presentation-detail.component';
import { TypePresentationUpdateComponent } from './type-presentation-update.component';
import { TypePresentationDeletePopupComponent } from './type-presentation-delete-dialog.component';
import { ITypePresentation } from 'app/shared/model/type-presentation.model';

@Injectable({ providedIn: 'root' })
export class TypePresentationResolve implements Resolve<ITypePresentation> {
    constructor(private service: TypePresentationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((typePresentation: HttpResponse<TypePresentation>) => typePresentation.body));
        }
        return of(new TypePresentation());
    }
}

export const typePresentationRoute: Routes = [
    {
        path: 'type-presentation',
        component: TypePresentationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypePresentations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-presentation/:id/view',
        component: TypePresentationDetailComponent,
        resolve: {
            typePresentation: TypePresentationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypePresentations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-presentation/new',
        component: TypePresentationUpdateComponent,
        resolve: {
            typePresentation: TypePresentationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypePresentations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-presentation/:id/edit',
        component: TypePresentationUpdateComponent,
        resolve: {
            typePresentation: TypePresentationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypePresentations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typePresentationPopupRoute: Routes = [
    {
        path: 'type-presentation/:id/delete',
        component: TypePresentationDeletePopupComponent,
        resolve: {
            typePresentation: TypePresentationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TypePresentations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
