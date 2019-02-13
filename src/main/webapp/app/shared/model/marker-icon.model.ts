export interface IMarkerIcon {
    id?: number;
    iconContentType?: string;
    icon?: any;
    shadowContentType?: string;
    shadow?: any;
    iconSize?: string;
    shadowSize?: string;
    iconAnchor?: string;
    shadowAnchor?: string;
    popupAnchor?: string;
}

export class MarkerIcon implements IMarkerIcon {
    constructor(
        public id?: number,
        public iconContentType?: string,
        public icon?: any,
        public shadowContentType?: string,
        public shadow?: any,
        public iconSize?: string,
        public shadowSize?: string,
        public iconAnchor?: string,
        public shadowAnchor?: string,
        public popupAnchor?: string
    ) {}
}
