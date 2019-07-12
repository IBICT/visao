import { Moment } from 'moment';
import { IMetaDado } from 'app/shared/model//meta-dado.model';
import { IUser } from 'app/core/user/user.model';
import { ITypePresentation } from 'app/shared/model//type-presentation.model';
import { ICategory } from 'app/shared/model//category.model';

export interface IGrupIndicator {
    id?: number;
    name?: string;
    active?: boolean;
    description?: any;
    keyWord?: string;
    date?: Moment;
    source?: string;
    dateChange?: Moment;
    note?: any;
    metaDados?: IMetaDado[];
    owner?: IUser;
    typePresentation?: ITypePresentation;
    categories?: ICategory[];
}

export class GrupIndicator implements IGrupIndicator {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public description?: any,
        public keyWord?: string,
        public date?: Moment,
        public source?: string,
        public dateChange?: Moment,
        public note?: any,
        public metaDados?: IMetaDado[],
        public owner?: IUser,
        public typePresentation?: ITypePresentation,
        public categories?: ICategory[]
    ) {
        this.active = false;
    }
}
