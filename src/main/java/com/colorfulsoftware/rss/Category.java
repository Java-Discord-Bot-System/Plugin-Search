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
 * The &lt;category> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * FOR channels: Specify one or more categories that the channel belongs to.
 * Follows the same rules as the &lt;item>-level <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >category</a> element. More <a
 * href="http://cyber.law.harvard.edu/rss/rss.html#syndic8">info</a>.
 * </p>
 * 
 * <p>
 * FOR items: Includes the item in one or more categories. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcategorygtSubelementOfLtitemgt"
 * >More</a>.
 * 
 * <p>
 * &lt;category> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * It has one optional attribute, domain, a string that identifies a
 * categorization taxonomy.
 * </p>
 * 
 * <p>
 * The value of the element is a forward-slash-separated string that identifies
 * a hierarchic location in the indicated taxonomy. Processors may establish
 * conventions for the interpretation of categories. Two examples are provided
 * below:
 * </p>
 * 
 * <p>
 * &lt;category>Grateful Dead&lt;/category>
 * </p>
 * 
 * <p>
 * &lt;category domain="http://www.fool.com/cusips">MSFT&lt;/category>
 * </p>
 * 
 * <p>
 * You may include as many category elements as you need to, for different
 * domains, and to have an item cross-referenced in different parts of the same
 * domain.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Category implements Serializable {

	private static final long serialVersionUID = 3499943299756566396L;

	private final String category;

	private final Attribute domain;

	Category(Attribute domain, String category) throws RSSpectException {
		this.domain = (domain == null) ? null : new Attribute(domain);
		// spec doesn't require it but category should be present
		if (category == null) {
			throw new RSSpectException(
					"Category elements SHOULD contain text data.  Empty strings are allowed.");
		}
		this.category = category;
	}

	Category(Category category) {
		this.domain = category.getDomain();
		this.category = category.category;
	}

	/**
	 * @return the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the domain url for the category.
	 */
	public Attribute getDomain() {
		return (domain == null) ? null : new Attribute(domain);

	}

	/**
	 * Shows the contents of the &lt;category> element.
	 */
	@Override
	public String toString() {
		return "<category" + ((domain == null) ? ">" : domain + " >")
				+ category + "</category>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Category)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}

}
