import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export const enum TypePermission {
    PUBLIC = 'PUBLIC',
    PRIVATE = 'PRIVATE',
    SHARED = 'SHARED'
}

export interface IGroupCategory {
    id?: number;
    iconPresentation?: string;
    iconContentType?: string;
    about?: string;
    permission?: TypePermission;
    name?: string;
    owner?: IUser;
    categories?: ICategory[];
    shareds?: IUser[];
}

export class GroupCategory implements IGroupCategory {
    constructor(
        public id?: number,
        public iconPresentation?: string,
        public iconContentType?: string,
        public about?: string,
        public permission?: TypePermission,
        public name?: string,
        public owner?: IUser,
        public categories?: ICategory[],
        public shareds?: IUser[]
    ) {}
}
