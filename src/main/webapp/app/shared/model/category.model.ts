export const enum TypeCategory {
    INDICATOR = 'INDICATOR',
    FILTER = 'FILTER',
    LAYER = 'LAYER'
}

export interface ICategory {
    id?: number;
    name?: string;
    type?: TypeCategory;
    level?: number;
}

export class Category implements ICategory {
    constructor(public id?: number, public name?: string, public type?: TypeCategory, public level?: number) {}
}
