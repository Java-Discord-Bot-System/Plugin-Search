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
 * The &lt;author> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Email address of the author of the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltauthorgtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;author> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * It's the email address of the author of the item. For newspapers and
 * magazines syndicating via RSS, the author is the person who wrote the article
 * that the &lt;item> describes. For collaborative weblogs, the author of the
 * item might be different from the managing editor or webmaster. For a weblog
 * authored by a single individual it would make sense to omit the &lt;author>
 * element.
 * </p>
 * 
 * <p>
 * &lt;author>lawyer@boyer.net (Lawyer Boyer)&lt;/author>
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Author implements Serializable {

	private static final long serialVersionUID = -547859529015538572L;

	private final String author;

	Author(String author) throws RSSpectException {
		if (author == null || author.equals("")) {
			throw new RSSpectException("Author names SHOULD NOT be blank.");
		}
		this.author = author;
	}

	Author(Author author) {
		this.author = author.author;
	}

	/**
	 * @return the author's email address and maybe more text.
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Shows the contents of the &lt;author> element.
	 */
	@Override
	public String toString() {
		return "<author>" + author + "</author>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Author)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}

}
