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

/**
 * <p>
 * The &lt;guid> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A string that uniquely identifies the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltguidgtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;guid> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * guid stands for globally unique identifier. It's a string that uniquely
 * identifies the item. When present, an aggregator may choose to use this
 * string to determine if an item is new.
 * </p>
 * 
 * <p>
 * &lt;guid>http://some.server.com/weblogItem3207&lt;/guid>
 * </p>
 * 
 * <p>
 * There are no rules for the syntax of a guid. Aggregators must view them as a
 * string. It's up to the source of the feed to establish the uniqueness of the
 * string.
 * </p>
 * 
 * <p>
 * If the guid element has an attribute named "isPermaLink" with a value of
 * true, the reader may assume that it is a permalink to the item, that is, a
 * url that can be opened in a Web browser, that points to the full item
 * described by the &lt;item> element. An example:
 * </p>
 * 
 * <p>
 * &lt;guid
 * isPermaLink="true">http://inessential.com/2002/09/01.php#a2&lt;/guid>
 * </p>
 * 
 * <p>
 * isPermaLink is optional, its default value is true. If its value is false,
 * the guid may not be assumed to be a url, or a url to anything in particular.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class GUID implements Serializable {

	private static final long serialVersionUID = 1285259651943559185L;

	private final String guid;
	private Attribute isPermaLink;

	GUID(Attribute isPermaLink, String guid) throws RSSpectException {
		this.isPermaLink = (isPermaLink == null) ? null : new Attribute(
				isPermaLink);
		if (guid == null || guid.equals("")) {
			throw new RSSpectException("guid SHOULD NOT be blank.");
		}
		this.guid = guid;
	}

	GUID(GUID guid) {
		this.isPermaLink = guid.getIsPermaLink();
		this.guid = guid.guid;
	}

	/**
	 * @return the guid.
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @return the isPermaLink attribute.
	 */
	public Attribute getIsPermaLink() {
		return (isPermaLink == null) ? null : new Attribute(isPermaLink);
	}

	/**
	 * Shows the contents of the &lt;guid> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<guid");

		// conditionally add the domain.
		sb.append(((isPermaLink == null) ? ">" : isPermaLink + " >") + guid
				+ "</guid>");

		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof GUID)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
