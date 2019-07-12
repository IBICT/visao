import { IMetaDado } from 'app/shared/model//meta-dado.model';
import { IUser } from 'app/core/user/user.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IGroupLayer {
    id?: number;
    name?: string;
    active?: boolean;
    keyWord?: string;
    metaDados?: IMetaDado[];
    owner?: IUser;
    categories?: ICategory[];
}

export class GroupLayer implements IGroupLayer {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public keyWord?: string,
        public metaDados?: IMetaDado[],
        public owner?: IUser,
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
