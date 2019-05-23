import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export const enum Permission {
    PUBLIC = 'PUBLIC',
    PRIVATE = 'PRIVATE',
    SHARED = 'SHARED'
}

export interface IGrupCategory {
    id?: number;
    logoContentType?: string;
    logo?: any;
    sobre?: any;
    permission?: Permission;
    owner?: IUser;
    categories?: ICategory[];
    shareds?: IUser[];
}

export class GrupCategory implements IGrupCategory {
    constructor(
        public id?: number,
        public logoContentType?: string,
        public logo?: any,
        public sobre?: any,
        public permission?: Permission,
        public owner?: IUser,
        public categories?: ICategory[],
        public shareds?: IUser[]
    ) {}
}
