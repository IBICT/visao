import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { IndicatorComponentsPage, IndicatorUpdatePage } from './indicator.page-object';

describe('Indicator e2e test', () => {
    let navBarPage: NavBarPage;
    let indicatorUpdatePage: IndicatorUpdatePage;
    let indicatorComponentsPage: IndicatorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Indicators', () => {
        navBarPage.goToEntity('indicator');
        indicatorComponentsPage = new IndicatorComponentsPage();
        expect(indicatorComponentsPage.getTitle()).toMatch(/Indicators/);
    });

    it('should load create Indicator page', () => {
        indicatorComponentsPage.clickOnCreateButton();
        indicatorUpdatePage = new IndicatorUpdatePage();
        expect(indicatorUpdatePage.getPageTitle()).toMatch(/Create or edit a Indicator/);
        indicatorUpdatePage.cancel();
    });

    it('should create and save Indicators', () => {
        indicatorComponentsPage.clickOnCreateButton();
        indicatorUpdatePage.setValueInput('5');
        expect(indicatorUpdatePage.getValueInput()).toMatch('5');
        indicatorUpdatePage.regionSelectLastOption();
        indicatorUpdatePage.groupSelectLastOption();
        indicatorUpdatePage.yearSelectLastOption();
        indicatorUpdatePage.save();
        expect(indicatorUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
