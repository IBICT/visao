import { Moment } from 'moment';
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
    description?: any;
    date?: Moment;
    source?: string;
    dateChange?: Moment;
    note?: any;
    icon?: IMarkerIcon;
    group?: IGroupLayer;
}

export class Layer implements ILayer {
    constructor(
        public id?: number,
        public name?: string,
        public geoJson?: any,
        public type?: TypeLayer,
        public description?: any,
        public date?: Moment,
        public source?: string,
        public dateChange?: Moment,
        public note?: any,
        public icon?: IMarkerIcon,
        public group?: IGroupLayer
    ) {}
}
