import { NgModule } from '@angular/core';

import { VisaoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [VisaoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [VisaoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class VisaoSharedCommonModule {}
