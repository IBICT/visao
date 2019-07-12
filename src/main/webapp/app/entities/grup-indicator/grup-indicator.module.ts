import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    GrupIndicatorComponent,
    GrupIndicatorDetailComponent,
    GrupIndicatorUpdateComponent,
    GrupIndicatorDeletePopupComponent,
    GrupIndicatorDeleteDialogComponent,
    grupIndicatorRoute,
    grupIndicatorPopupRoute
} from './';

const ENTITY_STATES = [...grupIndicatorRoute, ...grupIndicatorPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GrupIndicatorComponent,
        GrupIndicatorDetailComponent,
        GrupIndicatorUpdateComponent,
        GrupIndicatorDeleteDialogComponent,
        GrupIndicatorDeletePopupComponent
    ],
    entryComponents: [
        GrupIndicatorComponent,
        GrupIndicatorUpdateComponent,
        GrupIndicatorDeleteDialogComponent,
        GrupIndicatorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoGrupIndicatorModule {}
