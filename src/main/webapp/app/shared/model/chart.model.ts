import { IUser } from 'app/core/user/user.model';

export const enum Permission {
    PUBLIC = 'PUBLIC',
    PRIVATE = 'PRIVATE',
    SHARED = 'SHARED'
}

export interface IChart {
    id?: number;
    name?: string;
    html?: any;
    permission?: Permission;
    owner?: IUser;
    shareds?: IUser[];
}

export class Chart implements IChart {
    constructor(
        public id?: number,
        public name?: string,
        public html?: any,
        public permission?: Permission,
        public owner?: IUser,
        public shareds?: IUser[]
    ) {}
}
