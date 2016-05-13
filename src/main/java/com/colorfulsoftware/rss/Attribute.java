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
 * This class contains the attribute definition for elements.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Attribute implements Serializable {

	private static final long serialVersionUID = -3880416791234118400L;
	private final String name;
	private final String value;

	// use the factory method in the RSSDoc.
	Attribute(String name, String value) throws RSSpectException {
		if (name == null || name.equals("")) {
			throw new RSSpectException("Attribute names SHOULD NOT be blank.");
		}
		this.name = name;
		this.value = (value == null) ? "" : value;
	}

	// copy constructor
	Attribute(Attribute attribute) {
		this.name = attribute.name;
		this.value = attribute.value;
	}

	/**
	 * 
	 * @return the name of this attribute
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the value of this attribute
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return true if the attribute name and value are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Attribute)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Shows the contents of the element's attribute in the form of '
	 * attrName="attrValue"'.
	 */
	@Override
	public String toString() {
		return " " + name + "=\"" + value + "\"";
	}
}
