import { element, by, promise, ElementFinder } from 'protractor';

export class MetaDadoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-meta-dado div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class MetaDadoUpdatePage {
    pageTitle = element(by.id('jhi-meta-dado-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    valueInput = element(by.id('field_value'));
    indicatorSelect = element(by.id('field_indicator'));
    grupIndicatorSelect = element(by.id('field_grupIndicator'));
    geographicFilterSelect = element(by.id('field_geographicFilter'));
    layerSelect = element(by.id('field_layer'));
    groupLayerSelect = element(by.id('field_groupLayer'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setValueInput(value): promise.Promise<void> {
        return this.valueInput.sendKeys(value);
    }

    getValueInput() {
        return this.valueInput.getAttribute('value');
    }

    indicatorSelectLastOption(): promise.Promise<void> {
        return this.indicatorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    indicatorSelectOption(option): promise.Promise<void> {
        return this.indicatorSelect.sendKeys(option);
    }

    getIndicatorSelect(): ElementFinder {
        return this.indicatorSelect;
    }

    getIndicatorSelectedOption() {
        return this.indicatorSelect.element(by.css('option:checked')).getText();
    }

    grupIndicatorSelectLastOption(): promise.Promise<void> {
        return this.grupIndicatorSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    grupIndicatorSelectOption(option): promise.Promise<void> {
        return this.grupIndicatorSelect.sendKeys(option);
    }

    getGrupIndicatorSelect(): ElementFinder {
        return this.grupIndicatorSelect;
    }

    getGrupIndicatorSelectedOption() {
        return this.grupIndicatorSelect.element(by.css('option:checked')).getText();
    }

    geographicFilterSelectLastOption(): promise.Promise<void> {
        return this.geographicFilterSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    geographicFilterSelectOption(option): promise.Promise<void> {
        return this.geographicFilterSelect.sendKeys(option);
    }

    getGeographicFilterSelect(): ElementFinder {
        return this.geographicFilterSelect;
    }

    getGeographicFilterSelectedOption() {
        return this.geographicFilterSelect.element(by.css('option:checked')).getText();
    }

    layerSelectLastOption(): promise.Promise<void> {
        return this.layerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    layerSelectOption(option): promise.Promise<void> {
        return this.layerSelect.sendKeys(option);
    }

    getLayerSelect(): ElementFinder {
        return this.layerSelect;
    }

    getLayerSelectedOption() {
        return this.layerSelect.element(by.css('option:checked')).getText();
    }

    groupLayerSelectLastOption(): promise.Promise<void> {
        return this.groupLayerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    groupLayerSelectOption(option): promise.Promise<void> {
        return this.groupLayerSelect.sendKeys(option);
    }

    getGroupLayerSelect(): ElementFinder {
        return this.groupLayerSelect;
    }

    getGroupLayerSelectedOption() {
        return this.groupLayerSelect.element(by.css('option:checked')).getText();
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
