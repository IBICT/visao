import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from '../shared';

import { SOBRE_ROUTE, SobreComponent } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([SOBRE_ROUTE], { useHash: true })],
    declarations: [SobreComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppSobreModule {}
