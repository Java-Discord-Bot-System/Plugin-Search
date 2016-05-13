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
 * The &lt;webMaster> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Email address for person responsible for technical issues relating to
 * channel.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class WebMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2326329055869744204L;

	private final String webMaster;

	WebMaster(String webMaster) throws RSSpectException {
		if (webMaster == null || webMaster.equals("")) {
			throw new RSSpectException("webMaster SHOULD NOT be blank.");
		}
		this.webMaster = webMaster;
	}

	WebMaster(WebMaster webMaster) {
		this.webMaster = webMaster.webMaster;
	}

	/**
	 * @return the web master.
	 */
	public String getWebMaster() {
		return webMaster;
	}

	/**
	 * Shows the contents of the &lt;webMaster> element.
	 */
	@Override
	public String toString() {
		return "<webMaster>" + webMaster + "</webMaster>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof WebMaster)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
