import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    GroupCategoryComponent,
    GroupCategoryDetailComponent,
    GroupCategoryUpdateComponent,
    GroupCategoryDeletePopupComponent,
    GroupCategoryDeleteDialogComponent,
    groupCategoryRoute,
    groupCategoryPopupRoute
} from './';

const ENTITY_STATES = [...groupCategoryRoute, ...groupCategoryPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GroupCategoryComponent,
        GroupCategoryDetailComponent,
        GroupCategoryUpdateComponent,
        GroupCategoryDeleteDialogComponent,
        GroupCategoryDeletePopupComponent
    ],
    entryComponents: [
        GroupCategoryComponent,
        GroupCategoryUpdateComponent,
        GroupCategoryDeleteDialogComponent,
        GroupCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoGroupCategoryModule {}
