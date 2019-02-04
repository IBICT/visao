import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { LayerComponentsPage, LayerUpdatePage } from './layer.page-object';
import * as path from 'path';

describe('Layer e2e test', () => {
    let navBarPage: NavBarPage;
    let layerUpdatePage: LayerUpdatePage;
    let layerComponentsPage: LayerComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

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
        layerUpdatePage.setGeoJsonInput(absolutePath);
        layerUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    layerUpdatePage.getActiveInput().click();
                    expect(layerUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    layerUpdatePage.getActiveInput().click();
                    expect(layerUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        layerUpdatePage.setDescriptionInput('description');
        expect(layerUpdatePage.getDescriptionInput()).toMatch('description');
        layerUpdatePage.setKeyWordInput('keyWord');
        expect(layerUpdatePage.getKeyWordInput()).toMatch('keyWord');
        layerUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(layerUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        layerUpdatePage.setProducerInput('producer');
        expect(layerUpdatePage.getProducerInput()).toMatch('producer');
        layerUpdatePage.setSourceInput('source');
        expect(layerUpdatePage.getSourceInput()).toMatch('source');
        layerUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(layerUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        layerUpdatePage.setNoteInput('note');
        expect(layerUpdatePage.getNoteInput()).toMatch('note');
        layerUpdatePage.save();
        expect(layerUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
