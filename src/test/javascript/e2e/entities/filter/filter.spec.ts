import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { FilterComponentsPage, FilterUpdatePage } from './filter.page-object';

describe('Filter e2e test', () => {
    let navBarPage: NavBarPage;
    let filterUpdatePage: FilterUpdatePage;
    let filterComponentsPage: FilterComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Filters', () => {
        navBarPage.goToEntity('filter');
        filterComponentsPage = new FilterComponentsPage();
        expect(filterComponentsPage.getTitle()).toMatch(/visaoApp.filter.home.title/);
    });

    it('should load create Filter page', () => {
        filterComponentsPage.clickOnCreateButton();
        filterUpdatePage = new FilterUpdatePage();
        expect(filterUpdatePage.getPageTitle()).toMatch(/visaoApp.filter.home.createOrEditLabel/);
        filterUpdatePage.cancel();
    });

    it('should create and save Filters', () => {
        filterComponentsPage.clickOnCreateButton();
        filterUpdatePage.setNameInput('name');
        expect(filterUpdatePage.getNameInput()).toMatch('name');
        filterUpdatePage
            .getActiveInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    filterUpdatePage.getActiveInput().click();
                    expect(filterUpdatePage.getActiveInput().isSelected()).toBeFalsy();
                } else {
                    filterUpdatePage.getActiveInput().click();
                    expect(filterUpdatePage.getActiveInput().isSelected()).toBeTruthy();
                }
            });
        filterUpdatePage.setDescriptionInput('description');
        expect(filterUpdatePage.getDescriptionInput()).toMatch('description');
        filterUpdatePage.setKeyWordInput('keyWord');
        expect(filterUpdatePage.getKeyWordInput()).toMatch('keyWord');
        filterUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(filterUpdatePage.getDateInput()).toContain('2001-01-01T02:30');
        filterUpdatePage.setSourceInput('source');
        expect(filterUpdatePage.getSourceInput()).toMatch('source');
        filterUpdatePage.setDateChangeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(filterUpdatePage.getDateChangeInput()).toContain('2001-01-01T02:30');
        filterUpdatePage.setNoteInput('note');
        expect(filterUpdatePage.getNoteInput()).toMatch('note');
        filterUpdatePage.cidadePoloSelectLastOption();
        filterUpdatePage.userSelectLastOption();
        // filterUpdatePage.regionSelectLastOption();
        // filterUpdatePage.categorySelectLastOption();
        filterUpdatePage.save();
        expect(filterUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
