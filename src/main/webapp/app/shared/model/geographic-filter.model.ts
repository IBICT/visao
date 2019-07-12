import { Moment } from 'moment';
import { IRegion } from 'app/shared/model//region.model';
import { IMetaDado } from 'app/shared/model//meta-dado.model';
import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IGeographicFilter {
    id?: number;
    name?: string;
    active?: boolean;
    description?: any;
    keyWord?: string;
    date?: Moment;
    source?: string;
    dateChange?: Moment;
    note?: any;
    cidadePolo?: IRegion;
    metaDados?: IMetaDado[];
    owner?: IUser;
    regions?: IRegion[];
    categories?: ICategory[];
}

export class GeographicFilter implements IGeographicFilter {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public description?: any,
        public keyWord?: string,
        public date?: Moment,
        public source?: string,
        public dateChange?: Moment,
        public note?: any,
        public cidadePolo?: IRegion,
        public metaDados?: IMetaDado[],
        public owner?: IUser,
        public regions?: IRegion[],
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
