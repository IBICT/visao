import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    GeographicFilterComponent,
    GeographicFilterDetailComponent,
    GeographicFilterUpdateComponent,
    GeographicFilterDeletePopupComponent,
    GeographicFilterDeleteDialogComponent,
    geographicFilterRoute,
    geographicFilterPopupRoute
} from './';

const ENTITY_STATES = [...geographicFilterRoute, ...geographicFilterPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GeographicFilterComponent,
        GeographicFilterDetailComponent,
        GeographicFilterUpdateComponent,
        GeographicFilterDeleteDialogComponent,
        GeographicFilterDeletePopupComponent
    ],
    entryComponents: [
        GeographicFilterComponent,
        GeographicFilterUpdateComponent,
        GeographicFilterDeleteDialogComponent,
        GeographicFilterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoGeographicFilterModule {}
