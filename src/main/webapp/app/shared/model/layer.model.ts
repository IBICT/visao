import { Moment } from 'moment';
import { ICategory } from 'app/shared/model//category.model';
import { IMarkerIcon } from 'app/shared/model//marker-icon.model';
import { IGroupLayer } from 'app/shared/model//group-layer.model';

export const enum TypeLayer {
    MARKER = 'MARKER',
    CIRCLE = 'CIRCLE',
    POLYGON = 'POLYGON',
    ICON = 'ICON'
}

export interface ILayer {
    id?: number;
    name?: string;
    geoJson?: any;
    type?: TypeLayer;
    description?: string;
    date?: Moment;
    source?: string;
    dateChange?: Moment;
    note?: string;
    category?: ICategory;
    icon?: IMarkerIcon;
    group?: IGroupLayer;
}

export class Layer implements ILayer {
    constructor(
        public id?: number,
        public name?: string,
        public geoJson?: any,
        public type?: TypeLayer,
        public description?: string,
        public date?: Moment,
        public source?: string,
        public dateChange?: Moment,
        public note?: string,
        public category?: ICategory,
        public icon?: IMarkerIcon,
        public group?: IGroupLayer
    ) {}
}
