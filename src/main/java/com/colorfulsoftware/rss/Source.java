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
 * The &lt;source> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The RSS channel that the item came from. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltsourcegtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;source> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * Its value is the name of the RSS channel that the item came from, derived
 * from its &lt;title>. It has one required attribute, url, which links to the
 * XMLization of the source.
 * </p>
 * 
 * <p>
 * &lt;source url="http://www.tomalak.org/links2.xml">Tomalak's
 * Realm&lt;/source>
 * </p>
 * 
 * <p>
 * The purpose of this element is to propagate credit for links, to publicize
 * the sources of news items. It can be used in the Post command of an
 * aggregator. It should be generated automatically when forwarding an item from
 * an aggregator to a weblog authoring tool.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4585022053173517802L;

	private final String source;
	private final Attribute url;

	Source(Attribute url, String source) throws RSSpectException {
		// make sure the url attribute is present
		if (url == null || !url.getName().equals("url")) {
			throw new RSSpectException(
					"source elements MUST contain a url attribute.");
		}
		this.url = new Attribute(url.getName(), url.getValue());

		if (source == null || source.equals("")) {
			throw new RSSpectException("source SHOULD NOT be blank.");
		}
		this.source = source;
	}

	Source(Source source) {
		this.url = source.getUrl();
		this.source = source.source;
	}

	/**
	 * @return the url attribute.
	 */
	public Attribute getUrl() {
		return new Attribute(url);
	}

	/**
	 * @return the source data.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Shows the contents of the &lt;source> element.
	 */
	@Override
	public String toString() {
		return "<source" + url + " >" + source + "</source>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Source)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}

}
