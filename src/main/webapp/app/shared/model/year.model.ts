export interface IYear {
    id?: number;
    date?: string;
}

export class Year implements IYear {
    constructor(public id?: number, public date?: string) {}
}
