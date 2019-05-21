import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GroupCategoryComponentsPage, GroupCategoryUpdatePage } from './group-category.page-object';

describe('GroupCategory e2e test', () => {
    let navBarPage: NavBarPage;
    let groupCategoryUpdatePage: GroupCategoryUpdatePage;
    let groupCategoryComponentsPage: GroupCategoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GroupCategories', () => {
        navBarPage.goToEntity('group-category');
        groupCategoryComponentsPage = new GroupCategoryComponentsPage();
        expect(groupCategoryComponentsPage.getTitle()).toMatch(/visaoApp.groupCategory.home.title/);
    });

    it('should load create GroupCategory page', () => {
        groupCategoryComponentsPage.clickOnCreateButton();
        groupCategoryUpdatePage = new GroupCategoryUpdatePage();
        expect(groupCategoryUpdatePage.getPageTitle()).toMatch(/visaoApp.groupCategory.home.createOrEditLabel/);
        groupCategoryUpdatePage.cancel();
    });

    it('should create and save GroupCategories', () => {
        groupCategoryComponentsPage.clickOnCreateButton();
        groupCategoryUpdatePage.setIconPresentationInput('iconPresentation');
        expect(groupCategoryUpdatePage.getIconPresentationInput()).toMatch('iconPresentation');
        groupCategoryUpdatePage.setIconContentTypeInput('iconContentType');
        expect(groupCategoryUpdatePage.getIconContentTypeInput()).toMatch('iconContentType');
        groupCategoryUpdatePage.setAboutInput('about');
        expect(groupCategoryUpdatePage.getAboutInput()).toMatch('about');
        groupCategoryUpdatePage.permissionSelectLastOption();
        groupCategoryUpdatePage.ownerSelectLastOption();
        // groupCategoryUpdatePage.categoriesSelectLastOption();
        // groupCategoryUpdatePage.sharedsSelectLastOption();
        groupCategoryUpdatePage.save();
        expect(groupCategoryUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
