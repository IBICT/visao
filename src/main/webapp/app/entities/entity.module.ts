import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VisaoIndicatorModule } from './indicator/indicator.module';
import { VisaoNameModule } from './name/name.module';
import { VisaoCategoryModule } from './category/category.module';
import { VisaoRegionModule } from './region/region.module';
import { VisaoYearModule } from './year/year.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VisaoIndicatorModule,
        VisaoNameModule,
        VisaoCategoryModule,
        VisaoRegionModule,
        VisaoYearModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoEntityModule {}
