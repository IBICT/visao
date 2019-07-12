import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-visoes',
    templateUrl: './visoes.component.html',
    styleUrls: ['visoes.css']
})
export class VisoesComponent implements OnInit {
    message: string;

    constructor(private router: Router) {
        this.message = 'VisoesComponent message';
    }

    ngOnInit() {}
}
