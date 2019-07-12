import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GeographicFilterComponentsPage, GeographicFilterUpdatePage } from './geographic-filter.page-object';

describe('GeographicFilter e2e test', () => {
    let navBarPage: NavBarPage;
    let geographicFilterUpdatePage: GeographicFilterUpdatePage;
    let geographicFilterComponentsPage: GeographicFilterComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GeographicFilters', () => {
        navBarPage.goToEntity('geographic-filter');
        geographicFilterComponentsPage = new GeographicFilterComponentsPage();
        expect(geographicFilterComponentsPage.getTitle()).toMatch(/Geographic Filters/);
    });

    it('should load create GeographicFilter page', () => {
        geographicFilterComponentsPage.clickOnCreateButton();
        geographicFilterUpdatePage = new GeographicFilterUpdatePage();
        expect(geographicFilterUpdatePage.getPageTitle()).toMatch(/Create or edit a Geographic Filter/);
        geographicFilterUpdatePage.cancel();
    });

    it('should create and save GeographicFilters', () => {
        geographicFilterComponentsPage.clickOnCreateButton();
        geographicFilterUpdatePage.setNameInput('name');
        expect(geographicFilterUpdatePage.getNameInput()).toMatch('name');
        geographicFilterUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    geographicFilterUpdatePage.getActiveInput().click();
                    expect(geographicFilterUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    geographicFilterUpdatePage.getActiveInput().click();
                    expect(geographicFilterUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        geographicFilterUpdatePage.setDescriptionInput('description');
        expect(geographicFilterUpdatePage.getDescriptionInput()).toMatch('description');
        geographicFilterUpdatePage.setKeyWordInput('keyWord');
        expect(geographicFilterUpdatePage.getKeyWordInput()).toMatch('keyWord');
        geographicFilterUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(geographicFilterUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        geographicFilterUpdatePage.setSourceInput('source');
        expect(geographicFilterUpdatePage.getSourceInput()).toMatch('source');
        geographicFilterUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(geographicFilterUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        geographicFilterUpdatePage.setNoteInput('note');
        expect(geographicFilterUpdatePage.getNoteInput()).toMatch('note');
        geographicFilterUpdatePage.cidadePoloSelectLastOption();
        geographicFilterUpdatePage.ownerSelectLastOption();
        // geographicFilterUpdatePage.regionSelectLastOption();
        // geographicFilterUpdatePage.categorySelectLastOption();
        geographicFilterUpdatePage.save();
        expect(geographicFilterUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
