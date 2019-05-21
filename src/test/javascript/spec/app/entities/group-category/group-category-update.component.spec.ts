/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GroupCategoryUpdateComponent } from 'app/entities/group-category/group-category-update.component';
import { GroupCategoryService } from 'app/entities/group-category/group-category.service';
import { GroupCategory } from 'app/shared/model/group-category.model';

describe('Component Tests', () => {
    describe('GroupCategory Management Update Component', () => {
        let comp: GroupCategoryUpdateComponent;
        let fixture: ComponentFixture<GroupCategoryUpdateComponent>;
        let service: GroupCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupCategoryUpdateComponent]
            })
                .overrideTemplate(GroupCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroupCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupCategoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GroupCategory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupCategory = entity;
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
                    const entity = new GroupCategory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupCategory = entity;
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
