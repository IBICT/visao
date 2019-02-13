import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    FilterComponent,
    FilterDetailComponent,
    FilterUpdateComponent,
    FilterDeletePopupComponent,
    FilterDeleteDialogComponent,
    filterRoute,
    filterPopupRoute
} from './';

const ENTITY_STATES = [...filterRoute, ...filterPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FilterComponent, FilterDetailComponent, FilterUpdateComponent, FilterDeleteDialogComponent, FilterDeletePopupComponent],
    entryComponents: [FilterComponent, FilterUpdateComponent, FilterDeleteDialogComponent, FilterDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoFilterModule {}
