package com.macaca.android.testing.server.common;

import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

/**
 * @author xdf
 *
 */
public class Element {
	/**
	 *
	 */
	public UiObject2 element;
	/**
	 *
	 */
	public String id;

	/**
	 * @param id
	 * @param element
	 */
	Element(String id, UiObject2 element) {
		this.element = element;
		this.id = id;
	}

	/**
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public void click() {
		 element.click();
	}

	/**
	 * @return res
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public String getText() throws UiObjectNotFoundException {
		return element.getText();
	}

	public void clearText() throws UiObjectNotFoundException {
		element.clear();
	}

	/**
	 * @param text
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public void setText(String text) throws UiObjectNotFoundException {
		Configurator config = Configurator.getInstance();
		config.setKeyInjectionDelay(20);
		element.setText(text);
		config.setKeyInjectionDelay(0);
	}

	/**
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public boolean tap() throws UiObjectNotFoundException {
		element.click();
		return true;
	}

	/**
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public boolean doubleTap() throws UiObjectNotFoundException, Exception {
		element.click();
		Thread.sleep(100);
		element.click();
		return true;
	}

	public boolean isDisplayed() throws UiObjectNotFoundException {
		return true;
	}

	public UiObject2 getUiObject() {
		return this.element;
	}
}