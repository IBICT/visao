import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { LayerComponentsPage, LayerUpdatePage } from './layer.page-object';

describe('Layer e2e test', () => {
    let navBarPage: NavBarPage;
    let layerUpdatePage: LayerUpdatePage;
    let layerComponentsPage: LayerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Layers', () => {
        navBarPage.goToEntity('layer');
        layerComponentsPage = new LayerComponentsPage();
        expect(layerComponentsPage.getTitle()).toMatch(/visaoApp.layer.home.title/);
    });

    it('should load create Layer page', () => {
        layerComponentsPage.clickOnCreateButton();
        layerUpdatePage = new LayerUpdatePage();
        expect(layerUpdatePage.getPageTitle()).toMatch(/visaoApp.layer.home.createOrEditLabel/);
        layerUpdatePage.cancel();
    });

    it('should create and save Layers', () => {
        layerComponentsPage.clickOnCreateButton();
        layerUpdatePage.setNameInput('name');
        expect(layerUpdatePage.getNameInput()).toMatch('name');
        layerUpdatePage.setGeoJsonInput('geoJson');
        expect(layerUpdatePage.getGeoJsonInput()).toMatch('geoJson');
        layerUpdatePage.typeSelectLastOption();
        layerUpdatePage.setDescriptionInput('description');
        expect(layerUpdatePage.getDescriptionInput()).toMatch('description');
        layerUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(layerUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        layerUpdatePage.setSourceInput('source');
        expect(layerUpdatePage.getSourceInput()).toMatch('source');
        layerUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(layerUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        layerUpdatePage.setNoteInput('note');
        expect(layerUpdatePage.getNoteInput()).toMatch('note');
        layerUpdatePage.categorySelectLastOption();
        layerUpdatePage.iconSelectLastOption();
        layerUpdatePage.groupSelectLastOption();
        layerUpdatePage.save();
        expect(layerUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
