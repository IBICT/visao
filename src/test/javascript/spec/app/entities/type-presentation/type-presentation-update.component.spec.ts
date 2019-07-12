/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { TypePresentationUpdateComponent } from 'app/entities/type-presentation/type-presentation-update.component';
import { TypePresentationService } from 'app/entities/type-presentation/type-presentation.service';
import { TypePresentation } from 'app/shared/model/type-presentation.model';

describe('Component Tests', () => {
    describe('TypePresentation Management Update Component', () => {
        let comp: TypePresentationUpdateComponent;
        let fixture: ComponentFixture<TypePresentationUpdateComponent>;
        let service: TypePresentationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [TypePresentationUpdateComponent]
            })
                .overrideTemplate(TypePresentationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypePresentationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypePresentationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypePresentation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typePresentation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypePresentation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typePresentation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
