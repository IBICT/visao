import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { VisoesComponent } from './';

export const VISOES_ROUTE: Route = {
    path: 'visoes',
    component: VisoesComponent,
    data: {
        authorities: [],
        pageTitle: 'visoes.title'
    },
    canActivate: [UserRouteAccessService]
};
