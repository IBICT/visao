import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IGroupLayer {
    id?: number;
    name?: string;
    active?: boolean;
    keyWord?: string;
    user?: IUser;
    categories?: ICategory[];
}

export class GroupLayer implements IGroupLayer {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public keyWord?: string,
        public user?: IUser,
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
