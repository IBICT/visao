/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { TypePresentationDetailComponent } from 'app/entities/type-presentation/type-presentation-detail.component';
import { TypePresentation } from 'app/shared/model/type-presentation.model';

describe('Component Tests', () => {
    describe('TypePresentation Management Detail Component', () => {
        let comp: TypePresentationDetailComponent;
        let fixture: ComponentFixture<TypePresentationDetailComponent>;
        const route = ({ data: of({ typePresentation: new TypePresentation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [TypePresentationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypePresentationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypePresentationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typePresentation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
