import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-login',
    templateUrl: './login.component.html',
    styleUrls: ['login.css']
})
export class LoginComponent implements OnInit {
    message: string;

    constructor() {
        this.message = 'LoginComponent message';
    }

    ngOnInit() {}
}
