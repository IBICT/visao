import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VisaoIndicatorModule } from './indicator/indicator.module';
import { VisaoNameModule } from './name/name.module';
import { VisaoCategoryModule } from './category/category.module';
import { VisaoRegionModule } from './region/region.module';
import { VisaoYearModule } from './year/year.module';
import { VisaoMetaDadoModule } from './meta-dado/meta-dado.module';
import { VisaoFilterModule } from './filter/filter.module';
import { VisaoLayerModule } from './layer/layer.module';
import { VisaoMarkerIconModule } from './marker-icon/marker-icon.module';
import { VisaoGroupLayerModule } from './group-layer/group-layer.module';
import { VisaoTypePresentationModule } from './type-presentation/type-presentation.module';
import { VisaoGroupCategoryModule } from './group-category/group-category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VisaoIndicatorModule,
        VisaoNameModule,
        VisaoCategoryModule,
        VisaoRegionModule,
        VisaoYearModule,
        VisaoMetaDadoModule,
        VisaoFilterModule,
        VisaoLayerModule,
        VisaoMarkerIconModule,
        VisaoGroupLayerModule,
        VisaoTypePresentationModule,
        VisaoGroupCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoEntityModule {}
