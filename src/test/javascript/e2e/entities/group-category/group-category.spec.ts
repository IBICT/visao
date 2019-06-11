import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GroupCategoryComponentsPage, GroupCategoryUpdatePage } from './group-category.page-object';
import * as path from 'path';

describe('GroupCategory e2e test', () => {
    let navBarPage: NavBarPage;
    let groupCategoryUpdatePage: GroupCategoryUpdatePage;
    let groupCategoryComponentsPage: GroupCategoryComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

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
        groupCategoryUpdatePage.setAboutInput('about');
        expect(groupCategoryUpdatePage.getAboutInput()).toMatch('about');
        groupCategoryUpdatePage.permissionSelectLastOption();
        groupCategoryUpdatePage.setNameInput('name');
        expect(groupCategoryUpdatePage.getNameInput()).toMatch('name');
        groupCategoryUpdatePage.setIconPresentationInput(absolutePath);
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
