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
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A URL that points to the documentation for the format used in the RSS file.
 * It's probably a pointer to this page. It's for people who might stumble
 * across an RSS file on a Web server 25 years from now and wonder what it is.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Docs implements Serializable {

	private static final long serialVersionUID = 1840987541596737383L;

	private final String docs;

	Docs(String docs) throws RSSpectException {
		if (docs == null || docs.equals("")) {
			throw new RSSpectException("docs SHOULD NOT be blank.");
		}
		this.docs = docs;
	}

	Docs(Docs docs) {
		this.docs = docs.docs;
	}

	/**
	 * @return the documentation information for the rss format in url form.
	 */
	public String getDocs() {
		return docs;
	}

	/**
	 * Shows the contents of the &lt;docs> element.
	 */
	@Override
	public String toString() {
		return "<docs>" + docs + "</docs>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Docs)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}

}
