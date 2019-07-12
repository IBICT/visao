import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { VisaoComponent } from './';

export const VISAO_ROUTE: Route = {
    path: 'visao',
    component: VisaoComponent,
    data: {
        authorities: [],
        pageTitle: 'visao.title'
    },
    canActivate: [UserRouteAccessService]
};
