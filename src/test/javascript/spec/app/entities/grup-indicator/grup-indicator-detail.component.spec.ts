/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GrupIndicatorDetailComponent } from 'app/entities/grup-indicator/grup-indicator-detail.component';
import { GrupIndicator } from 'app/shared/model/grup-indicator.model';

describe('Component Tests', () => {
    describe('GrupIndicator Management Detail Component', () => {
        let comp: GrupIndicatorDetailComponent;
        let fixture: ComponentFixture<GrupIndicatorDetailComponent>;
        const route = ({ data: of({ grupIndicator: new GrupIndicator(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupIndicatorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GrupIndicatorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupIndicatorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.grupIndicator).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
