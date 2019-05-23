/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GrupCategoryUpdateComponent } from 'app/entities/grup-category/grup-category-update.component';
import { GrupCategoryService } from 'app/entities/grup-category/grup-category.service';
import { GrupCategory } from 'app/shared/model/grup-category.model';

describe('Component Tests', () => {
    describe('GrupCategory Management Update Component', () => {
        let comp: GrupCategoryUpdateComponent;
        let fixture: ComponentFixture<GrupCategoryUpdateComponent>;
        let service: GrupCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GrupCategoryUpdateComponent]
            })
                .overrideTemplate(GrupCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupCategoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GrupCategory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupCategory = entity;
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
                    const entity = new GrupCategory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupCategory = entity;
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
