import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from '../shared';

import { VISAO_ROUTE, VisaoComponent } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([VISAO_ROUTE], { useHash: true })],
    declarations: [VisaoComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppVisaoModule {}
