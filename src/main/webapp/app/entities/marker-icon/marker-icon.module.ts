import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    MarkerIconComponent,
    MarkerIconDetailComponent,
    MarkerIconUpdateComponent,
    MarkerIconDeletePopupComponent,
    MarkerIconDeleteDialogComponent,
    markerIconRoute,
    markerIconPopupRoute
} from './';

const ENTITY_STATES = [...markerIconRoute, ...markerIconPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MarkerIconComponent,
        MarkerIconDetailComponent,
        MarkerIconUpdateComponent,
        MarkerIconDeleteDialogComponent,
        MarkerIconDeletePopupComponent
    ],
    entryComponents: [MarkerIconComponent, MarkerIconUpdateComponent, MarkerIconDeleteDialogComponent, MarkerIconDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoMarkerIconModule {}
