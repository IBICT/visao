import { IUser } from 'app/core/user/user.model';

export interface IGroupLayer {
    id?: number;
    name?: string;
    active?: boolean;
    keyWord?: string;
    user?: IUser;
}

export class GroupLayer implements IGroupLayer {
    constructor(public id?: number, public name?: string, public active?: boolean, public keyWord?: string, public user?: IUser) {
        this.active = false;
    }
}
