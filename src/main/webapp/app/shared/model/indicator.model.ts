import { IMetaDado } from 'app/shared/model//meta-dado.model';
import { IRegion } from 'app/shared/model//region.model';
import { IGrupIndicator } from 'app/shared/model//grup-indicator.model';
import { IYear } from 'app/shared/model//year.model';

export interface IIndicator {
    id?: number;
    value?: number;
    metaDados?: IMetaDado[];
    region?: IRegion;
    group?: IGrupIndicator;
    year?: IYear;
}

export class Indicator implements IIndicator {
    constructor(
        public id?: number,
        public value?: number,
        public metaDados?: IMetaDado[],
        public region?: IRegion,
        public group?: IGrupIndicator,
        public year?: IYear
    ) {}
}
