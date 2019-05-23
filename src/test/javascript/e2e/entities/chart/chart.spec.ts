import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ChartComponentsPage, ChartUpdatePage } from './chart.page-object';

describe('Chart e2e test', () => {
    let navBarPage: NavBarPage;
    let chartUpdatePage: ChartUpdatePage;
    let chartComponentsPage: ChartComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Charts', () => {
        navBarPage.goToEntity('chart');
        chartComponentsPage = new ChartComponentsPage();
        expect(chartComponentsPage.getTitle()).toMatch(/visaoApp.chart.home.title/);
    });

    it('should load create Chart page', () => {
        chartComponentsPage.clickOnCreateButton();
        chartUpdatePage = new ChartUpdatePage();
        expect(chartUpdatePage.getPageTitle()).toMatch(/visaoApp.chart.home.createOrEditLabel/);
        chartUpdatePage.cancel();
    });

    it('should create and save Charts', () => {
        chartComponentsPage.clickOnCreateButton();
        chartUpdatePage.setNameInput('name');
        expect(chartUpdatePage.getNameInput()).toMatch('name');
        chartUpdatePage.setHtmlInput('html');
        expect(chartUpdatePage.getHtmlInput()).toMatch('html');
        chartUpdatePage.permissionSelectLastOption();
        chartUpdatePage.ownerSelectLastOption();
        // chartUpdatePage.sharedSelectLastOption();
        chartUpdatePage.save();
        expect(chartUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
