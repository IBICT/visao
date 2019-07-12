import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { LoginComponent } from './';

export const LOGIN_ROUTE: Route = {
    path: 'login',
    component: LoginComponent,
    data: {
        authorities: [],
        pageTitle: 'login.title'
    },
    canActivate: [UserRouteAccessService]
};
