import { IRegion } from 'app/shared/model//region.model';

export const enum TypeRegion {
    ESTADO = 'ESTADO',
    MESORREGIAO = 'MESORREGIAO',
    MUNICIPIO = 'MUNICIPIO'
}

export interface IRegion {
    id?: number;
    name?: string;
    uf?: string;
    geoCode?: number;
    typeRegion?: TypeRegion;
    relacaos?: IRegion[];
}

export class Region implements IRegion {
    constructor(
        public id?: number,
        public name?: string,
        public uf?: string,
        public geoCode?: number,
        public typeRegion?: TypeRegion,
        public relacaos?: IRegion[]
    ) {}
}
