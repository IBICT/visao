export interface ITypePresentation {
    id?: number;
    name?: string;
    display?: string;
    description?: any;
}

export class TypePresentation implements ITypePresentation {
    constructor(public id?: number, public name?: string, public display?: string, public description?: any) {}
}
