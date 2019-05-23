import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import { VisaoAdminModule } from 'app/admin/admin.module';
import {
    ChartComponent,
    ChartDetailComponent,
    ChartUpdateComponent,
    ChartDeletePopupComponent,
    ChartDeleteDialogComponent,
    chartRoute,
    chartPopupRoute
} from './';

const ENTITY_STATES = [...chartRoute, ...chartPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, VisaoAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ChartComponent, ChartDetailComponent, ChartUpdateComponent, ChartDeleteDialogComponent, ChartDeletePopupComponent],
    entryComponents: [ChartComponent, ChartUpdateComponent, ChartDeleteDialogComponent, ChartDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoChartModule {}
