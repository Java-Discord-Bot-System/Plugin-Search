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
 * The &lt;language> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The language the channel is written in. This allows aggregators to group all
 * Italian language sites, for example, on a single page. A list of allowable
 * values for this element, as provided by Netscape, is <a
 * href="http://cyber.law.harvard.edu/rss/languages.html">here</a>. You may also
 * use <a
 * href="http://www.w3.org/TR/REC-html40/struct/dirlang.html#langcodes">values
 * defined</a> by the W3C.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Language implements Serializable {

	private static final long serialVersionUID = -8639326685256827986L;

	private final String language;

	Language(String language) throws RSSpectException {
		if (language == null || language.equals("")) {
			throw new RSSpectException("language SHOULD NOT be blank.");
		}
		this.language = language;
	}

	Language(Language language) {
		this.language = language.language;
	}

	/**
	 * @return the language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Shows the contents of the &lt;language> element.
	 */
	@Override
	public String toString() {
		return "<language>" + language + "</language>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Language)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
