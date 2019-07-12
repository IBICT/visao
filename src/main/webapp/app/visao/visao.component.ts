import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-visao',
    templateUrl: './visao.component.html',
    styleUrls: ['visao.css']
})
export class VisaoComponent implements OnInit {
    chart: string;
    grupCategory: string;
    safeUrl: any;

    constructor(private sanitizer: DomSanitizer, private activatedRoute: ActivatedRoute) {
        this.activatedRoute.queryParams.subscribe(params => {
            this.chart = params['chart'];
            this.grupCategory = params['grupCategory'];
        });
    }

    ngOnInit() {
        this.getTrustedUrl('./visao/' + this.chart + '?key=' + this.grupCategory);
    }

    getTrustedUrl(url: any) {
        this.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}
