import { element, by, promise, ElementFinder } from 'protractor';

export class GroupCategoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-group-category div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GroupCategoryUpdatePage {
    pageTitle = element(by.id('jhi-group-category-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    iconPresentationInput = element(by.id('field_iconPresentation'));
    iconContentTypeInput = element(by.id('field_iconContentType'));
    aboutInput = element(by.id('field_about'));
    ownerSelect = element(by.id('field_owner'));
    categoriesSelect = element(by.id('field_categories'));
    sharedsSelect = element(by.id('field_shareds'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setIconPresentationInput(iconPresentation): promise.Promise<void> {
        return this.iconPresentationInput.sendKeys(iconPresentation);
    }

    getIconPresentationInput() {
        return this.iconPresentationInput.getAttribute('value');
    }

    setIconContentTypeInput(iconContentType): promise.Promise<void> {
        return this.iconContentTypeInput.sendKeys(iconContentType);
    }

    getIconContentTypeInput() {
        return this.iconContentTypeInput.getAttribute('value');
    }

    setAboutInput(about): promise.Promise<void> {
        return this.aboutInput.sendKeys(about);
    }

    getAboutInput() {
        return this.aboutInput.getAttribute('value');
    }

    ownerSelectLastOption(): promise.Promise<void> {
        return this.ownerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    ownerSelectOption(option): promise.Promise<void> {
        return this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect(): ElementFinder {
        return this.ownerSelect;
    }

    getOwnerSelectedOption() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    }

    categoriesSelectLastOption(): promise.Promise<void> {
        return this.categoriesSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    categoriesSelectOption(option): promise.Promise<void> {
        return this.categoriesSelect.sendKeys(option);
    }

    getCategoriesSelect(): ElementFinder {
        return this.categoriesSelect;
    }

    getCategoriesSelectedOption() {
        return this.categoriesSelect.element(by.css('option:checked')).getText();
    }

    sharedsSelectLastOption(): promise.Promise<void> {
        return this.sharedsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    sharedsSelectOption(option): promise.Promise<void> {
        return this.sharedsSelect.sendKeys(option);
    }

    getSharedsSelect(): ElementFinder {
        return this.sharedsSelect;
    }

    getSharedsSelectedOption() {
        return this.sharedsSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
