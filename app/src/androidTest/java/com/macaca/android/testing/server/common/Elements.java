package com.macaca.android.testing.server.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiObject2;

/**
 * @author xdf
 *
 */
public class Elements {

	/**
	 * global singleton
	 */
	private static Elements global;
	/**
	 * 
	 */
	private Hashtable<String, Element> elems;
	private UiDevice mDevice;

	/**
	 * Constructor
	 */
	public Elements() {
		elems = new Hashtable<String, Element>();
		mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
	}

	/**
	 * @return res
	 */
	public static Elements getGlobal() {
		if (Elements.global == null) {
			Elements.global = new Elements();
		}
		return Elements.global;
	}

	/**
	 * @param element
	 * @return res
	 */
	public Element addElement(UiObject2 element) {
		Element elem = new Element("1", element);
		getElems().put("1", elem);
		return elem;
	}

	public List<Element> addElements(List<UiObject2> elements) {
		List<Element> elems = new ArrayList<Element>();
		for(int i = 0; i < elements.size(); i++) {
			int index = i + 1;
			Element elem = new Element(index + "", elements.get(i));
			getElems().put(index + "", elem);
			elems.add(elem);
		}

		return elems;
	}

	/**
	 * @param key
	 * @return res
	 */
	public Element getElement(String key) {
		return getElems().get(key);
	}

	/**
	 * @param sel
	 * @return res
	 * @throws Exception
	 */
	public Element getElement(BySelector sel) throws Exception {
		UiObject2 el = mDevice.findObject(sel);
		Element result = addElement(el);
		if (el != null) {
			return result;
		} else {
			throw new Exception("not found");
		}
	}

	public List<Element> getMultiElement(BySelector sel) throws Exception {
		List<UiObject2> el = mDevice.findObjects(sel);
		List<Element> result = addElements(el);
		if (result != null) {
			return result;
		} else {
			throw new Exception("not found");
		}
	}

//	/**
//	 * @param selector
//	 * @return res
//	 * @throws UiObjectNotFoundException
//	 */
//	public ArrayList<Element> getElements(UiSelector selector)
//			throws UiObjectNotFoundException {
//		String str = selector.toString();
//		boolean index = str.contains("CLASS_REGEX=");
//		boolean endsInstance = Pattern.compile(".*INSTANCE=\\d+]$")
//				.matcher(str).matches();
//
//		ArrayList<Element> elements = new ArrayList<Element>();
//
//		if (endsInstance) {
//			UiObject obj = new UiObject(selector);
//			if (obj != null && obj.exists()) {
//				elements.add(addElement(obj));
//			}
//			return elements;
//		}
//
//		UiObject obj;
//		UiSelector tmp;
//		int counter = 0;
//
//		boolean keep = true;
//		while (keep) {
//			if (index) {
//				tmp = selector.index(counter);
//			} else {
//				tmp = selector.instance(counter);
//			}
//			obj = new UiObject(tmp);
//			counter++;
//
//			if (obj != null && obj.exists()) {
//				elements.add(addElement(obj));
//			} else {
//				keep = false;
//			}
//		}
//		return elements;
//	}

	/**
	 * @param instance
	 */
	public static void setInstance(Elements instance) {
		Elements.global = instance;
	}

	/**
	 * @return res
	 */
	public Hashtable<String, Element> getElems() {
		return elems;
	}
}