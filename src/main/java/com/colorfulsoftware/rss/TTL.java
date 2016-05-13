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
 * The &lt;ttl> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. More info <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltttlgtSubelementOfLtchannelgt"
 * >here</a>.
 * </p>
 * 
 * <p>
 * &lt;ttl> is an optional sub-element of &lt;channel>.
 * </p>
 * 
 * <p>
 * ttl stands for time to live. It's a number of minutes that indicates how long
 * a channel can be cached before refreshing from the source. This makes it
 * possible for RSS sources to be managed by a file-sharing network such as
 * Gnutella.
 * </p>
 * 
 * <p>
 * Example: <ttl>60</ttl>
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class TTL implements Serializable {

	private static final long serialVersionUID = -6668886988682614060L;

	private final String ttl;

	TTL(String ttl) throws RSSpectException {
		if (ttl == null || ttl.equals("")) {
			throw new RSSpectException("ttl SHOULD NOT be blank.");
		}
		this.ttl = ttl;
	}

	TTL(TTL ttl) {
		this.ttl = ttl.ttl;
	}

	/**
	 * @return the time to live.
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * Shows the contents of the &lt;ttl> element.
	 */
	@Override
	public String toString() {
		return "<ttl>" + ttl + "</ttl>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TTL)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
