import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SobreComponent } from './';

export const SOBRE_ROUTE: Route = {
    path: 'sobre',
    component: SobreComponent,
    data: {
        authorities: [],
        pageTitle: 'sobre.title'
    },
    canActivate: [UserRouteAccessService]
};
