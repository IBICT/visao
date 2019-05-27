import { Moment } from 'moment';
import { IRegion } from 'app/shared/model//region.model';
import { ICategory } from 'app/shared/model//category.model';
import { IUser } from 'app/core/user/user.model';

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
    category?: ICategory;
    user?: IUser;
    regions?: IRegion[];
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
        public category?: ICategory,
        public user?: IUser,
        public regions?: IRegion[]
    ) {
        this.active = false;
    }
}
