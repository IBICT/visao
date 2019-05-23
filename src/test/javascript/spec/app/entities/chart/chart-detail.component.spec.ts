/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { ChartDetailComponent } from 'app/entities/chart/chart-detail.component';
import { Chart } from 'app/shared/model/chart.model';

describe('Component Tests', () => {
    describe('Chart Management Detail Component', () => {
        let comp: ChartDetailComponent;
        let fixture: ComponentFixture<ChartDetailComponent>;
        const route = ({ data: of({ chart: new Chart(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [ChartDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChartDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChartDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chart).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
