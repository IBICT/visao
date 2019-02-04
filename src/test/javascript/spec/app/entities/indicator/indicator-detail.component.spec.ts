/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { IndicatorDetailComponent } from 'app/entities/indicator/indicator-detail.component';
import { Indicator } from 'app/shared/model/indicator.model';

describe('Component Tests', () => {
    describe('Indicator Management Detail Component', () => {
        let comp: IndicatorDetailComponent;
        let fixture: ComponentFixture<IndicatorDetailComponent>;
        const route = ({ data: of({ indicator: new Indicator(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [IndicatorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IndicatorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IndicatorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.indicator).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
