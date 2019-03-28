import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { MapComponent } from './map.component';

export const MAP_ROUTE: Route = {
    path: 'map',
    component: MapComponent,
    data: {
        authorities: [],
        pageTitle: 'map.title'
    },
    canActivate: [UserRouteAccessService]
};
