import { Moment } from 'moment';

export interface ILayer {
    id?: number;
    name?: string;
    geoJsonContentType?: string;
    geoJson?: any;
    active?: boolean;
    description?: string;
    keyWord?: string;
    date?: Moment;
    producer?: string;
    source?: string;
    dateChange?: Moment;
    note?: string;
}

export class Layer implements ILayer {
    constructor(
        public id?: number,
        public name?: string,
        public geoJsonContentType?: string,
        public geoJson?: any,
        public active?: boolean,
        public description?: string,
        public keyWord?: string,
        public date?: Moment,
        public producer?: string,
        public source?: string,
        public dateChange?: Moment,
        public note?: string
    ) {
        this.active = false;
    }
}
