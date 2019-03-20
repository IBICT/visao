import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from 'app/shared';
import {
    TypePresentationComponent,
    TypePresentationDetailComponent,
    TypePresentationUpdateComponent,
    TypePresentationDeletePopupComponent,
    TypePresentationDeleteDialogComponent,
    typePresentationRoute,
    typePresentationPopupRoute
} from './';

const ENTITY_STATES = [...typePresentationRoute, ...typePresentationPopupRoute];

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypePresentationComponent,
        TypePresentationDetailComponent,
        TypePresentationUpdateComponent,
        TypePresentationDeleteDialogComponent,
        TypePresentationDeletePopupComponent
    ],
    entryComponents: [
        TypePresentationComponent,
        TypePresentationUpdateComponent,
        TypePresentationDeleteDialogComponent,
        TypePresentationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoTypePresentationModule {}
