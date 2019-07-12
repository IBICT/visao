import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-sobre',
    templateUrl: './sobre.component.html',
    styleUrls: ['sobre.css']
})
export class SobreComponent implements OnInit {
    message: string;

    constructor() {
        this.message = 'SobreComponent message';
    }

    ngOnInit() {}
}
