import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from '../shared';

import { MAP_ROUTE, MapComponent } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([MAP_ROUTE], { useHash: true })],
    declarations: [MapComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppMapModule {}
