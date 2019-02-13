/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { MarkerIconDetailComponent } from 'app/entities/marker-icon/marker-icon-detail.component';
import { MarkerIcon } from 'app/shared/model/marker-icon.model';

describe('Component Tests', () => {
    describe('MarkerIcon Management Detail Component', () => {
        let comp: MarkerIconDetailComponent;
        let fixture: ComponentFixture<MarkerIconDetailComponent>;
        const route = ({ data: of({ markerIcon: new MarkerIcon(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [MarkerIconDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MarkerIconDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MarkerIconDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.markerIcon).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
