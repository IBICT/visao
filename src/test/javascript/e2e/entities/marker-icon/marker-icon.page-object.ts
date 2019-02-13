import { element, by, promise, ElementFinder } from 'protractor';

export class MarkerIconComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-marker-icon div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MarkerIconUpdatePage {
    pageTitle = element(by.id('jhi-marker-icon-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    iconInput = element(by.id('file_icon'));
    shadowInput = element(by.id('file_shadow'));
    iconSizeInput = element(by.id('field_iconSize'));
    shadowSizeInput = element(by.id('field_shadowSize'));
    iconAnchorInput = element(by.id('field_iconAnchor'));
    shadowAnchorInput = element(by.id('field_shadowAnchor'));
    popupAnchorInput = element(by.id('field_popupAnchor'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setIconInput(icon): promise.Promise<void> {
        return this.iconInput.sendKeys(icon);
    }

    getIconInput() {
        return this.iconInput.getAttribute('value');
    }

    setShadowInput(shadow): promise.Promise<void> {
        return this.shadowInput.sendKeys(shadow);
    }

    getShadowInput() {
        return this.shadowInput.getAttribute('value');
    }

    setIconSizeInput(iconSize): promise.Promise<void> {
        return this.iconSizeInput.sendKeys(iconSize);
    }

    getIconSizeInput() {
        return this.iconSizeInput.getAttribute('value');
    }

    setShadowSizeInput(shadowSize): promise.Promise<void> {
        return this.shadowSizeInput.sendKeys(shadowSize);
    }

    getShadowSizeInput() {
        return this.shadowSizeInput.getAttribute('value');
    }

    setIconAnchorInput(iconAnchor): promise.Promise<void> {
        return this.iconAnchorInput.sendKeys(iconAnchor);
    }

    getIconAnchorInput() {
        return this.iconAnchorInput.getAttribute('value');
    }

    setShadowAnchorInput(shadowAnchor): promise.Promise<void> {
        return this.shadowAnchorInput.sendKeys(shadowAnchor);
    }

    getShadowAnchorInput() {
        return this.shadowAnchorInput.getAttribute('value');
    }

    setPopupAnchorInput(popupAnchor): promise.Promise<void> {
        return this.popupAnchorInput.sendKeys(popupAnchor);
    }

    getPopupAnchorInput() {
        return this.popupAnchorInput.getAttribute('value');
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
