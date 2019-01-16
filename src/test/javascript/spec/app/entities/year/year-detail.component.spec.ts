/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { YearDetailComponent } from 'app/entities/year/year-detail.component';
import { Year } from 'app/shared/model/year.model';

describe('Component Tests', () => {
    describe('Year Management Detail Component', () => {
        let comp: YearDetailComponent;
        let fixture: ComponentFixture<YearDetailComponent>;
        const route = ({ data: of({ year: new Year(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [YearDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(YearDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(YearDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.year).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
