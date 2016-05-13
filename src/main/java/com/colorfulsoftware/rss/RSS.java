/**
 * Copyright 2011 Bill Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * The &lt;rss> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * At the top level, a RSS document is a &lt;rss> element, with a mandatory
 * attribute called version, that specifies the version of RSS that the document
 * conforms to. If it conforms to this specification, the version attribute must
 * be 2.0.
 * </p>
 * 
 * <p>
 * Subordinate to the &lt;rss> element is a single &lt;channel> element, which
 * contains information about the channel (metadata) and its contents. RSS
 * originated in 1999, and has strived to be a simple, easy to understand
 * format, with relatively modest goals. After it became a popular format,
 * developers wanted to extend it using modules defined in namespaces, as
 * specified by the W3C.
 * </p>
 * 
 * <p>
 * RSS 2.0 adds that capability, following a simple rule. A RSS feed may contain
 * elements not described on this page, only if those elements are defined in a
 * namespace.
 * </p>
 * 
 * <p>
 * The elements defined in this document are not themselves members of a
 * namespace, so that RSS 2.0 can remain compatible with previous versions in
 * the following sense - - a version 0.91 or 0.92 file is also a valid 2.0 file.
 * If the elements of RSS 2.0 were in a namespace, this constraint would break,
 * a version 0.9x file would not be a valid 2.0 file.
 * </p>
 * 
 * <p>
 * The version="2.0" attribute of the &lt;rss> element is automatically provided
 * in this implementation.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class RSS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2952021194132587749L;

	private final Channel channel;
	private final List<Attribute> attributes;
	private final List<Extension> extensions;
	private List<String> unboundPrefixes;

	RSS(Channel channel, List<Attribute> attributes, List<Extension> extensions)
			throws RSSpectException {
		if (channel == null) {
			throw new RSSpectException(
					"rss elements MUST contain a channel element.");
		}

		this.channel = new Channel(channel);

		if (attributes == null) {
			throw new RSSpectException(
					"RSS elements must contain a version attribute.");
		} else {
			this.attributes = new LinkedList<Attribute>();

			boolean containsVersion = false;

			for (Attribute attr : attributes) {
				this.attributes.add(new Attribute(attr));
				if (attr.getName().equals("version")) {
					containsVersion = true;
				}
			}

			if (!containsVersion) {
				throw new RSSpectException(
						"RSS elements must contain a version attribute.");
			}
		}

		// check that all extension prefixes are bound to a namespace
		this.unboundPrefixes = new LinkedList<String>();

		if (this.channel.getUnboundPrefixes() != null) {
			for (String unboundPrefix : this.channel.getUnboundPrefixes()) {
				if (getAttribute("xmlns:" + unboundPrefix) == null) {
					this.unboundPrefixes.add(unboundPrefix);
				}
			}
		}

		if (extensions == null) {
			this.extensions = null;
		} else {
			this.extensions = new LinkedList<Extension>();
			for (Extension extension : extensions) {
				// check that the extension prefix is bound to a namespace
				String namespacePrefix = extension.getNamespacePrefix();
				if (namespacePrefix != null) {
					if (getAttribute("xmlns:" + namespacePrefix) == null) {
						this.unboundPrefixes.add(namespacePrefix);
					}
				}
				this.extensions.add(new Extension(extension));
			}
		}

		// if there are any unbound prefixes, throw an exception
		if (this.unboundPrefixes.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String namePrefix : this.unboundPrefixes) {
				sb.append(namePrefix + " ");
			}
			throw new RSSpectException(
					"the following extension prefix(es) ( "
							+ sb
							+ ") are not bound to a namespace declaration. See http://www.w3.org/TR/1999/REC-xml-names-19990114/#ns-decl.");
		}
	}

	/**
	 * @return the channel object.
	 */
	public Channel getChannel() {
		return new Channel(channel);
	}

	/**
	 * 
	 * @return the attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		for (Attribute attr : this.attributes) {
			attrsCopy.add(new Attribute(attr));

		}
		return attrsCopy;
	}

	/**
	 * 
	 * @return the extensions for this entry.
	 */
	public List<Extension> getExtensions() {
		if (extensions == null) {
			return null;
		}
		List<Extension> extsCopy = new LinkedList<Extension>();
		for (Extension extension : this.extensions) {
			extsCopy.add(new Extension(extension));
		}
		return extsCopy;
	}

	/**
	 * @param attrName
	 *            the name of the attribute to get.
	 * @return the Attribute object if attrName matches or null if not found.
	 */
	public Attribute getAttribute(String attrName) {
		for (Attribute attribute : this.attributes) {
			if (attribute.getName().equals(attrName)) {
				return new Attribute(attribute);

			}
		}
		return null;
	}

	/**
	 * @param extName
	 *            the element name of the extension. eg. "atom:link" or
	 *            "someExtension"
	 * @return the extension matching the element or null if not found.
	 */
	public Extension getExtension(String extName) {
		if (this.extensions != null) {
			for (Extension extension : this.extensions) {
				if (extension.getElementName().equals(extName)) {
					return new Extension(extension);
				}
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;rss> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<rss");
		for (Attribute attribute : attributes) {
			sb.append(attribute);
		}
		// close the parent element
		sb.append(">");

		sb.append(channel);

		if (extensions != null) {
			for (Extension extension : extensions) {
				sb.append(extension);
			}
		}

		sb.append("</rss>");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof RSS)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
