import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';

import { VisaoSharedModule } from '../shared';

import { MAP_ROUTE, MapComponent, MapService } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([MAP_ROUTE], { useHash: true }), LeafletModule.forRoot()],
    declarations: [MapComponent],
    entryComponents: [MapComponent],
    providers: [MapService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppMapModule {}
