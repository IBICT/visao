import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IName {
    id?: number;
    value?: string;
    active?: boolean;
    description?: any;
    keyWord?: string;
    date?: Moment;
    source?: string;
    dateChange?: Moment;
    note?: any;
    user?: IUser;
    categories?: ICategory[];
}

export class Name implements IName {
    constructor(
        public id?: number,
        public value?: string,
        public active?: boolean,
        public description?: any,
        public keyWord?: string,
        public date?: Moment,
        public source?: string,
        public dateChange?: Moment,
        public note?: any,
        public user?: IUser,
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
