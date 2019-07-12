import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    MetaDadoComponent,
    MetaDadoDetailComponent,
    MetaDadoUpdateComponent,
    MetaDadoDeletePopupComponent,
    MetaDadoDeleteDialogComponent,
    metaDadoRoute,
    metaDadoPopupRoute
} from './';

const ENTITY_STATES = [...metaDadoRoute, ...metaDadoPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MetaDadoComponent,
        MetaDadoDetailComponent,
        MetaDadoUpdateComponent,
        MetaDadoDeleteDialogComponent,
        MetaDadoDeletePopupComponent
    ],
    entryComponents: [MetaDadoComponent, MetaDadoUpdateComponent, MetaDadoDeleteDialogComponent, MetaDadoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoMetaDadoModule {}
