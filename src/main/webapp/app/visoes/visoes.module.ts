import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from '../shared';

import { VISOES_ROUTE, VisoesComponent } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([VISOES_ROUTE], { useHash: true })],
    declarations: [VisoesComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppVisoesModule {}
