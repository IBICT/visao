import { IRegion } from 'app/shared/model//region.model';
import { IName } from 'app/shared/model//name.model';
import { IYear } from 'app/shared/model//year.model';

export interface IIndicator {
    id?: number;
    value?: number;
    region?: IRegion;
    name?: IName;
    ano?: IYear;
}

export class Indicator implements IIndicator {
    constructor(public id?: number, public value?: number, public region?: IRegion, public name?: IName, public ano?: IYear) {}
}
