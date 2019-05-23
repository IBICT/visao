import { Moment } from 'moment';
import { IRegion } from 'app/shared/model//region.model';
import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IFilter {
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
    user?: IUser;
    regions?: IRegion[];
    categories?: ICategory[];
}

export class Filter implements IFilter {
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
        public user?: IUser,
        public regions?: IRegion[],
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
