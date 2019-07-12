import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    GroupLayerComponent,
    GroupLayerDetailComponent,
    GroupLayerUpdateComponent,
    GroupLayerDeletePopupComponent,
    GroupLayerDeleteDialogComponent,
    groupLayerRoute,
    groupLayerPopupRoute
} from './';

const ENTITY_STATES = [...groupLayerRoute, ...groupLayerPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GroupLayerComponent,
        GroupLayerDetailComponent,
        GroupLayerUpdateComponent,
        GroupLayerDeleteDialogComponent,
        GroupLayerDeletePopupComponent
    ],
    entryComponents: [GroupLayerComponent, GroupLayerUpdateComponent, GroupLayerDeleteDialogComponent, GroupLayerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoGroupLayerModule {}
