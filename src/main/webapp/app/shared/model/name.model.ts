import { Moment } from 'moment';
import { ICategory } from 'app/shared/model//category.model';
import { IUser } from 'app/core/user/user.model';

export interface IName {
    id?: number;
    value?: string;
    active?: boolean;
    description?: string;
    keyWord?: string;
    date?: Moment;
    producer?: string;
    source?: string;
    dateChange?: Moment;
    note?: string;
    category?: ICategory;
    user?: IUser;
}

export class Name implements IName {
    constructor(
        public id?: number,
        public value?: string,
        public active?: boolean,
        public description?: string,
        public keyWord?: string,
        public date?: Moment,
        public producer?: string,
        public source?: string,
        public dateChange?: Moment,
        public note?: string,
        public category?: ICategory,
        public user?: IUser
    ) {
        this.active = false;
    }
}
