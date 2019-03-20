export interface ITypePresentation {
    id?: number;
    name?: string;
    display?: string;
    description?: string;
}

export class TypePresentation implements ITypePresentation {
    constructor(public id?: number, public name?: string, public display?: string, public description?: string) {}
}
