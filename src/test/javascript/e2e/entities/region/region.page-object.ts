import { element, by, promise, ElementFinder } from 'protractor';

export class RegionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-region div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class RegionUpdatePage {
    pageTitle = element(by.id('jhi-region-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    ufInput = element(by.id('field_uf'));
    geoCodeInput = element(by.id('field_geoCode'));
    typeRegionSelect = element(by.id('field_typeRegion'));
    relacaoSelect = element(by.id('field_relacao'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setUfInput(uf): promise.Promise<void> {
        return this.ufInput.sendKeys(uf);
    }

    getUfInput() {
        return this.ufInput.getAttribute('value');
    }

    setGeoCodeInput(geoCode): promise.Promise<void> {
        return this.geoCodeInput.sendKeys(geoCode);
    }

    getGeoCodeInput() {
        return this.geoCodeInput.getAttribute('value');
    }

    setTypeRegionSelect(typeRegion): promise.Promise<void> {
        return this.typeRegionSelect.sendKeys(typeRegion);
    }

    getTypeRegionSelect() {
        return this.typeRegionSelect.element(by.css('option:checked')).getText();
    }

    typeRegionSelectLastOption(): promise.Promise<void> {
        return this.typeRegionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    relacaoSelectLastOption(): promise.Promise<void> {
        return this.relacaoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    relacaoSelectOption(option): promise.Promise<void> {
        return this.relacaoSelect.sendKeys(option);
    }

    getRelacaoSelect(): ElementFinder {
        return this.relacaoSelect;
    }

    getRelacaoSelectedOption() {
        return this.relacaoSelect.element(by.css('option:checked')).getText();
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
