/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { FilterDetailComponent } from 'app/entities/filter/filter-detail.component';
import { Filter } from 'app/shared/model/filter.model';

describe('Component Tests', () => {
    describe('Filter Management Detail Component', () => {
        let comp: FilterDetailComponent;
        let fixture: ComponentFixture<FilterDetailComponent>;
        const route = ({ data: of({ filter: new Filter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [FilterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FilterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.filter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
