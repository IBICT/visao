import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    YearComponent,
    YearDetailComponent,
    YearUpdateComponent,
    YearDeletePopupComponent,
    YearDeleteDialogComponent,
    yearRoute,
    yearPopupRoute
} from './';

const ENTITY_STATES = [...yearRoute, ...yearPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [YearComponent, YearDetailComponent, YearUpdateComponent, YearDeleteDialogComponent, YearDeletePopupComponent],
    entryComponents: [YearComponent, YearUpdateComponent, YearDeleteDialogComponent, YearDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoYearModule {}
