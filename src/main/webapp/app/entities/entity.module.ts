import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VisaoIndicatorModule } from './indicator/indicator.module';
import { VisaoGrupIndicatorModule } from './grup-indicator/grup-indicator.module';
import { VisaoTypePresentationModule } from './type-presentation/type-presentation.module';
import { VisaoCategoryModule } from './category/category.module';
import { VisaoRegionModule } from './region/region.module';
import { VisaoYearModule } from './year/year.module';
import { VisaoMetaDadoModule } from './meta-dado/meta-dado.module';
import { VisaoGeographicFilterModule } from './geographic-filter/geographic-filter.module';
import { VisaoLayerModule } from './layer/layer.module';
import { VisaoMarkerIconModule } from './marker-icon/marker-icon.module';
import { VisaoGroupLayerModule } from './group-layer/group-layer.module';
import { VisaoChartModule } from './chart/chart.module';
import { VisaoGrupCategoryModule } from './grup-category/grup-category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VisaoIndicatorModule,
        VisaoGrupIndicatorModule,
        VisaoTypePresentationModule,
        VisaoCategoryModule,
        VisaoRegionModule,
        VisaoYearModule,
        VisaoMetaDadoModule,
        VisaoGeographicFilterModule,
        VisaoLayerModule,
        VisaoMarkerIconModule,
        VisaoGroupLayerModule,
        VisaoChartModule,
        VisaoGrupCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VisaoEntityModule {}
