import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IRegion } from 'app/shared/model//region.model';

export interface IFilter {
    id?: number;
    name?: string;
    active?: boolean;
    description?: string;
    keyWord?: string;
    date?: Moment;
    producer?: string;
    source?: string;
    dateChange?: Moment;
    note?: string;
    user?: IUser;
    regions?: IRegion[];
}

export class Filter implements IFilter {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public description?: string,
        public keyWord?: string,
        public date?: Moment,
        public producer?: string,
        public source?: string,
        public dateChange?: Moment,
        public note?: string,
        public user?: IUser,
        public regions?: IRegion[]
    ) {
        this.active = false;
    }
}
