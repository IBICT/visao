/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GeographicFilterDetailComponent } from 'app/entities/geographic-filter/geographic-filter-detail.component';
import { GeographicFilter } from 'app/shared/model/geographic-filter.model';

describe('Component Tests', () => {
    describe('GeographicFilter Management Detail Component', () => {
        let comp: GeographicFilterDetailComponent;
        let fixture: ComponentFixture<GeographicFilterDetailComponent>;
        const route = ({ data: of({ geographicFilter: new GeographicFilter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GeographicFilterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GeographicFilterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GeographicFilterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.geographicFilter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
