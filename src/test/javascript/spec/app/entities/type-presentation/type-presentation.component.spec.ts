/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VisaoTestModule } from '../../../test.module';
import { TypePresentationComponent } from 'app/entities/type-presentation/type-presentation.component';
import { TypePresentationService } from 'app/entities/type-presentation/type-presentation.service';
import { TypePresentation } from 'app/shared/model/type-presentation.model';

describe('Component Tests', () => {
    describe('TypePresentation Management Component', () => {
        let comp: TypePresentationComponent;
        let fixture: ComponentFixture<TypePresentationComponent>;
        let service: TypePresentationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [TypePresentationComponent],
                providers: []
            })
                .overrideTemplate(TypePresentationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypePresentationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypePresentationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypePresentation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typePresentations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
