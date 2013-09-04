/**
 *
 */
package com.thinkinglogic.rest.mock;

import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.tools.generic.XmlTool;
import org.dom4j.Node;

/**
 * Wrapper around XmlTool that strips all namespace information, while ensuring the original aml is returned from
 * toString.
 * 
 */
public class XmlToolWrapper extends XmlTool {

	private XmlTool wrapped;
	private final String xml;

	/**
	 * @param xml the xml to create an XmlTool from.
	 */
	public XmlToolWrapper(final String xml) {
		try {
			this.wrapped = new XmlTool().parse(removeXmlNamespace(xml));
			if (this.wrapped == null) {
				this.wrapped = new XmlTool().parse(xml);
			}
		} catch (Exception e) {
			this.wrapped = new XmlTool().parse(xml);
		}
		this.xml = xml;
	}

	/**
	 * Uses regex to remove namespace information.
	 * 
	 * @param xmlString
	 * @return
	 */
	public static String removeXmlNamespace(String xmlString) {
		return xmlString.replaceAll("xmlns:", "") /* remove xmlns: */
		.replaceAll("</[^>]*?:", "</") /* remove closing tags prefix */
		.replaceAll("<[^>]*?:", "<"); /* remove opening tag prefix */
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}

	@Override
	public XmlTool read(Object o) {
		return wrapped.read(o);
	}

	@Override
	public XmlTool parse(Object o) {
		return wrapped.parse(o);
	}

	@Override
	public Object get(Object o) {
		return wrapped.get(o);
	}

	@Override
	public Object getName() {
		return wrapped.getName();
	}

	@Override
	public String getNodeName() {
		return wrapped.getNodeName();
	}

	@Override
	public String getPath() {
		return wrapped.getPath();
	}

	@Override
	public String attr(Object o) {
		return wrapped.attr(o);
	}

	@Override
	public Map<String, String> attributes() {
		return wrapped.attributes();
	}

	@Override
	public XmlTool children() {
		return wrapped.children();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void configure(Map params) {
		wrapped.configure(params);
	}

	@Override
	public boolean equals(Object obj) {
		return wrapped.equals(obj);
	}

	@Override
	public boolean isEmpty() {
		return wrapped.isEmpty();
	}

	@Override
	public int size() {
		return wrapped.size();
	}

	@Override
	public Iterator<XmlTool> iterator() {
		return wrapped.iterator();
	}

	@Override
	public XmlTool getFirst() {
		return wrapped.getFirst();
	}

	@Override
	public XmlTool getLast() {
		return wrapped.getLast();
	}

	@Override
	public XmlTool get(Number n) {
		return wrapped.get(n);
	}

	@Override
	public Node node() {
		return wrapped.node();
	}

	@Override
	public XmlTool find(Object o) {
		return wrapped.find(o);
	}

	@Override
	public XmlTool find(String xpath) {
		return wrapped.find(xpath);
	}

	@Override
	public XmlTool getParent() {
		return wrapped.getParent();
	}

	@Override
	public String getText() {
		return wrapped.getText();
	}

	@Override
	public boolean isConfigLocked() {
		return wrapped.isConfigLocked();
	}

	@Override
	public boolean isSafeMode() {
		return wrapped.isSafeMode();
	}

	@Override
	public XmlTool parents() {
		return wrapped.parents();
	}

	@Override
	public String toString() {
		return xml;
	}

}
