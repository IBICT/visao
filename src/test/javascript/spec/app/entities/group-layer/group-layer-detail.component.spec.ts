/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { GroupLayerDetailComponent } from 'app/entities/group-layer/group-layer-detail.component';
import { GroupLayer } from 'app/shared/model/group-layer.model';

describe('Component Tests', () => {
    describe('GroupLayer Management Detail Component', () => {
        let comp: GroupLayerDetailComponent;
        let fixture: ComponentFixture<GroupLayerDetailComponent>;
        const route = ({ data: of({ groupLayer: new GroupLayer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [GroupLayerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GroupLayerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GroupLayerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.groupLayer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
