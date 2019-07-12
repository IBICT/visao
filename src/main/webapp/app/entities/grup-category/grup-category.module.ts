import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    GrupCategoryComponent,
    GrupCategoryDetailComponent,
    GrupCategoryUpdateComponent,
    GrupCategoryDeletePopupComponent,
    GrupCategoryDeleteDialogComponent,
    grupCategoryRoute,
    grupCategoryPopupRoute
} from './';

const ENTITY_STATES = [...grupCategoryRoute, ...grupCategoryPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GrupCategoryComponent,
        GrupCategoryDetailComponent,
        GrupCategoryUpdateComponent,
        GrupCategoryDeleteDialogComponent,
        GrupCategoryDeletePopupComponent
    ],
    entryComponents: [
        GrupCategoryComponent,
        GrupCategoryUpdateComponent,
        GrupCategoryDeleteDialogComponent,
        GrupCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoGrupCategoryModule {}
