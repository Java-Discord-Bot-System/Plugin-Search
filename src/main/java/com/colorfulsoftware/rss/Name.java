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
 * The &lt;name> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The name of the text object in the text input area.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Name implements Serializable {

	private static final long serialVersionUID = 7870202584874783195L;

	private final String name;

	Name(String name) throws RSSpectException {
		if (name == null || name.equals("")) {
			throw new RSSpectException("name SHOULD NOT be blank.");
		}
		this.name = name;
	}

	Name(Name name) {
		this.name = name.name;
	}

	/**
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Shows the contents of the &lt;name> element.
	 */
	@Override
	public String toString() {
		return "<name>" + name + "</name>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Name)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
