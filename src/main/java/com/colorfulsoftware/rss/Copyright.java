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
 * The &lt;copyright> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Copyright notice for content in the channel.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Copyright implements Serializable {

	private static final long serialVersionUID = 26097518613338635L;

	private final String copyright;

	Copyright(String copyright) throws RSSpectException {
		if (copyright == null || copyright.equals("")) {
			throw new RSSpectException("copyright SHOULD NOT be blank.");
		}
		this.copyright = copyright;
	}

	Copyright(Copyright copyright) {
		this.copyright = copyright.copyright;
	}

	/**
	 * @return the copyright information.
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Shows the contents of the &lt;copyright> element.
	 */
	@Override
	public String toString() {
		return "<copyright>" + copyright + "</copyright>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Copyright)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
