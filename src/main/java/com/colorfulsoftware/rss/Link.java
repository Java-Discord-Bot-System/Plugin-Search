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
 * The &lt;link> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * For Channel: The URL to the HTML website corresponding to the channel.
 * </p>
 * 
 * <p>
 * For Item: The URL of the item.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258812777994100037L;

	private final String link;

	Link(String link) throws RSSpectException {

		if (link == null || link.equals("")) {
			throw new RSSpectException("link SHOULD NOT be blank.");
		}

		String linkLocal = link.trim();
		if (linkLocal.length() > 0
				&& new URIScheme().contains(linkLocal.substring(0, linkLocal
						.indexOf(":")))) {
			this.link = link;
		} else {
			throw new RSSpectException("link elements must start with a valid "
					+ "Uniform Resource Identifer (URI) Schemes.  "
					+ "See http://www.iana.org. Yours started with: '" + link
					+ "'");
		}
	}

	Link(Link link) {
		this.link = link.link;
	}

	/**
	 * @return the link information.
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * Shows the contents of the &lt;link> element.
	 */
	@Override
	public String toString() {
		return "<link>" + link + "</link>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Link)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
