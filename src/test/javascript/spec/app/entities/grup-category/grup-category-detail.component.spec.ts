/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GrupCategoryDetailComponent } from 'app/entities/grup-category/grup-category-detail.component';
import { GrupCategory } from 'app/shared/model/grup-category.model';

describe('Component Tests', () => {
    describe('GrupCategory Management Detail Component', () => {
        let comp: GrupCategoryDetailComponent;
        let fixture: ComponentFixture<GrupCategoryDetailComponent>;
        const route = ({ data: of({ grupCategory: new GrupCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GrupCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.grupCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
