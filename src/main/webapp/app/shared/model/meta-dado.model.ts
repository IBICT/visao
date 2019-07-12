import { IIndicator } from 'app/shared/model//indicator.model';
import { IGrupIndicator } from 'app/shared/model//grup-indicator.model';
import { IGeographicFilter } from 'app/shared/model//geographic-filter.model';
import { ILayer } from 'app/shared/model//layer.model';
import { IGroupLayer } from 'app/shared/model//group-layer.model';

export interface IMetaDado {
    id?: number;
    name?: string;
    value?: string;
    indicator?: IIndicator;
    grupIndicator?: IGrupIndicator;
    geographicFilter?: IGeographicFilter;
    layer?: ILayer;
    groupLayer?: IGroupLayer;
}

export class MetaDado implements IMetaDado {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public indicator?: IIndicator,
        public grupIndicator?: IGrupIndicator,
        public geographicFilter?: IGeographicFilter,
        public layer?: ILayer,
        public groupLayer?: IGroupLayer
    ) {}
}
