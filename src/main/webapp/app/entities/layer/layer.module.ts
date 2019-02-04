import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    LayerComponent,
    LayerDetailComponent,
    LayerUpdateComponent,
    LayerDeletePopupComponent,
    LayerDeleteDialogComponent,
    layerRoute,
    layerPopupRoute
} from './';

const ENTITY_STATES = [...layerRoute, ...layerPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LayerComponent, LayerDetailComponent, LayerUpdateComponent, LayerDeleteDialogComponent, LayerDeletePopupComponent],
    entryComponents: [LayerComponent, LayerUpdateComponent, LayerDeleteDialogComponent, LayerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoLayerModule {}
