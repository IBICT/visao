import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    IndicatorComponent,
    IndicatorDetailComponent,
    IndicatorUpdateComponent,
    IndicatorDeletePopupComponent,
    IndicatorDeleteDialogComponent,
    indicatorRoute,
    indicatorPopupRoute
} from './';

const ENTITY_STATES = [...indicatorRoute, ...indicatorPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IndicatorComponent,
        IndicatorDetailComponent,
        IndicatorUpdateComponent,
        IndicatorDeleteDialogComponent,
        IndicatorDeletePopupComponent
    ],
    entryComponents: [IndicatorComponent, IndicatorUpdateComponent, IndicatorDeleteDialogComponent, IndicatorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoIndicatorModule {}
