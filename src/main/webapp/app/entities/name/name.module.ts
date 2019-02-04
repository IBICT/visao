import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    NameComponent,
    NameDetailComponent,
    NameUpdateComponent,
    NameDeletePopupComponent,
    NameDeleteDialogComponent,
    nameRoute,
    namePopupRoute
} from './';

const ENTITY_STATES = [...nameRoute, ...namePopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [NameComponent, NameDetailComponent, NameUpdateComponent, NameDeleteDialogComponent, NameDeletePopupComponent],
    entryComponents: [NameComponent, NameUpdateComponent, NameDeleteDialogComponent, NameDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoNameModule {}
