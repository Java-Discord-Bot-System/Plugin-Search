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
 * The &lt;url> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * This class models a &lt;url> element in an rss feed.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class URL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336240801697282972L;

	private final String url;

	URL(String url) throws RSSpectException {

		if (url == null || url.equals("")) {
			throw new RSSpectException("url SHOULD NOT be blank.");
		}

		String urlLocal = url.trim();
		if (urlLocal.length() > 0
				&& new URIScheme().contains(urlLocal.substring(0, urlLocal
						.indexOf(":")))) {
			this.url = url;
		} else {
			throw new RSSpectException("link elements must start with a valid "
					+ "Uniform Resource Identifer (URI) Schemes.  "
					+ "See http://www.iana.org. Yours started with: '" + url
					+ "'");
		}

	}

	URL(URL url) {
		this.url = url.url;
	}

	/**
	 * @return the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Shows the contents of the &lt;url> element.
	 */
	@Override
	public String toString() {
		return "<url>" + url + "</url>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof URL)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
