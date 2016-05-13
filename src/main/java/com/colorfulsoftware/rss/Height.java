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
 * The &lt;element> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Maximum value for height is 400, default value is 31.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Height implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828264834229377611L;

	private final String height;

	Height(String height) throws RSSpectException {

		if (height == null || height.equals("")) {
			throw new RSSpectException("height SHOULD NOT be blank.");
		}

		try {
			int localHeight = Integer.parseInt(height);
			if (localHeight > 400) {
				throw new RSSpectException(
						"height cannot be greater than 400px.");
			}
		} catch (NumberFormatException n) {
			throw new RSSpectException("invalid number format for height.");
		}

		this.height = height;
	}

	Height(Height height) {
		this.height = height.height;
	}

	/**
	 * @return the height.
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Shows the contents of the &lt;height> element.
	 */
	@Override
	public String toString() {
		return "<height>" + height + "</height>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Height)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
