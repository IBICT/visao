/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GroupCategoryDetailComponent } from 'app/entities/group-category/group-category-detail.component';
import { GroupCategory } from 'app/shared/model/group-category.model';

describe('Component Tests', () => {
    describe('GroupCategory Management Detail Component', () => {
        let comp: GroupCategoryDetailComponent;
        let fixture: ComponentFixture<GroupCategoryDetailComponent>;
        const route = ({ data: of({ groupCategory: new GroupCategory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupCategoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GroupCategoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupCategoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.groupCategory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
