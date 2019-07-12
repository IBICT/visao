import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VisaoSharedModule } from '../shared';

import { LOGIN_ROUTE, LoginComponent } from './';

@NgModule({
    imports: [VisaoSharedModule, RouterModule.forRoot([LOGIN_ROUTE], { useHash: true })],
    declarations: [LoginComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoAppLoginModule {}
