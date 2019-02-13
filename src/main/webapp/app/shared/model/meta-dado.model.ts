import { IName } from 'app/shared/model//name.model';

export interface IMetaDado {
    id?: number;
    name?: string;
    value?: string;
    nomes?: IName[];
}

export class MetaDado implements IMetaDado {
    constructor(public id?: number, public name?: string, public value?: string, public nomes?: IName[]) {}
}
