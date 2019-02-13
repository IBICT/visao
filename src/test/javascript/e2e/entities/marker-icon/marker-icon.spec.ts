import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { MarkerIconComponentsPage, MarkerIconUpdatePage } from './marker-icon.page-object';
import * as path from 'path';

describe('MarkerIcon e2e test', () => {
    let navBarPage: NavBarPage;
    let markerIconUpdatePage: MarkerIconUpdatePage;
    let markerIconComponentsPage: MarkerIconComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MarkerIcons', () => {
        navBarPage.goToEntity('marker-icon');
        markerIconComponentsPage = new MarkerIconComponentsPage();
        expect(markerIconComponentsPage.getTitle()).toMatch(/visaoApp.markerIcon.home.title/);
    });

    it('should load create MarkerIcon page', () => {
        markerIconComponentsPage.clickOnCreateButton();
        markerIconUpdatePage = new MarkerIconUpdatePage();
        expect(markerIconUpdatePage.getPageTitle()).toMatch(/visaoApp.markerIcon.home.createOrEditLabel/);
        markerIconUpdatePage.cancel();
    });

    it('should create and save MarkerIcons', () => {
        markerIconComponentsPage.clickOnCreateButton();
        markerIconUpdatePage.setIconInput(absolutePath);
        markerIconUpdatePage.setShadowInput(absolutePath);
        markerIconUpdatePage.setIconSizeInput('iconSize');
        expect(markerIconUpdatePage.getIconSizeInput()).toMatch('iconSize');
        markerIconUpdatePage.setShadowSizeInput('shadowSize');
        expect(markerIconUpdatePage.getShadowSizeInput()).toMatch('shadowSize');
        markerIconUpdatePage.setIconAnchorInput('iconAnchor');
        expect(markerIconUpdatePage.getIconAnchorInput()).toMatch('iconAnchor');
        markerIconUpdatePage.setShadowAnchorInput('shadowAnchor');
        expect(markerIconUpdatePage.getShadowAnchorInput()).toMatch('shadowAnchor');
        markerIconUpdatePage.setPopupAnchorInput('popupAnchor');
        expect(markerIconUpdatePage.getPopupAnchorInput()).toMatch('popupAnchor');
        markerIconUpdatePage.save();
        expect(markerIconUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
