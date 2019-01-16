import { ICategory } from 'app/shared/model//category.model';

export interface IName {
    id?: number;
    value?: string;
    category?: ICategory;
}

export class Name implements IName {
    constructor(public id?: number, public value?: string, public category?: ICategory) {}
}
