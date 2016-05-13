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
 * The &lt;description> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * For Channel: Phrase or sentence describing the channel.
 * </p>
 * 
 * <p>
 * For Item: The item synopsis.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Description implements Serializable {

	private static final long serialVersionUID = -3376088317656959708L;

	private final String description;

	Description(String description) {
		// not sure why, but descriptions can be blank.
		this.description = (description == null) ? "" : description;
	}
	
	Description(Description description){
		this.description = description.description;
	}

	/**
	 * @return the description (can be the empty string).
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Shows the contents of the &lt;description> element.
	 */
	@Override
	public String toString() {
		return "<description>" + description + "</description>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Description)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
