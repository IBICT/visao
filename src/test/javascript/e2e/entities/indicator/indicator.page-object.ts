import { element, by, promise, ElementFinder } from 'protractor';

export class IndicatorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-indicator div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndicatorUpdatePage {
    pageTitle = element(by.id('jhi-indicator-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    valueInput = element(by.id('field_value'));
    regionSelect = element(by.id('field_region'));
    nameSelect = element(by.id('field_name'));
    yearSelect = element(by.id('field_year'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setValueInput(value): promise.Promise<void> {
        return this.valueInput.sendKeys(value);
    }

    getValueInput() {
        return this.valueInput.getAttribute('value');
    }

    regionSelectLastOption(): promise.Promise<void> {
        return this.regionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    regionSelectOption(option): promise.Promise<void> {
        return this.regionSelect.sendKeys(option);
    }

    getRegionSelect(): ElementFinder {
        return this.regionSelect;
    }

    getRegionSelectedOption() {
        return this.regionSelect.element(by.css('option:checked')).getText();
    }

    nameSelectLastOption(): promise.Promise<void> {
        return this.nameSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    nameSelectOption(option): promise.Promise<void> {
        return this.nameSelect.sendKeys(option);
    }

    getNameSelect(): ElementFinder {
        return this.nameSelect;
    }

    getNameSelectedOption() {
        return this.nameSelect.element(by.css('option:checked')).getText();
    }

    yearSelectLastOption(): promise.Promise<void> {
        return this.yearSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    yearSelectOption(option): promise.Promise<void> {
        return this.yearSelect.sendKeys(option);
    }

    getYearSelect(): ElementFinder {
        return this.yearSelect;
    }

    getYearSelectedOption() {
        return this.yearSelect.element(by.css('option:checked')).getText();
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
