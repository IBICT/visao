/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VisaoTestModule } from '../../../test.module';
import { LayerDetailComponent } from 'app/entities/layer/layer-detail.component';
import { Layer } from 'app/shared/model/layer.model';

describe('Component Tests', () => {
    describe('Layer Management Detail Component', () => {
        let comp: LayerDetailComponent;
        let fixture: ComponentFixture<LayerDetailComponent>;
        const route = ({ data: of({ layer: new Layer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VisaoTestModule],
                declarations: [LayerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LayerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LayerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.layer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
