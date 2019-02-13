import { Moment } from 'moment';
import { ICategory } from 'app/shared/model//category.model';
import { IUser } from 'app/core/user/user.model';

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
    category?: ICategory;
    user?: IUser;
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
        public category?: ICategory,
        public user?: IUser
    ) {
        this.active = false;
    }
}
