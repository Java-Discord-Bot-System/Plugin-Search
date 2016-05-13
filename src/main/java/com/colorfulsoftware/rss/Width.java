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
 * The &lt;width> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Maximum value for width is 144, default value is 88.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Width implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591451698889716382L;

	private final String width;

	Width(String width) throws RSSpectException {

		if (width == null || width.equals("")) {
			throw new RSSpectException("width SHOULD NOT be blank.");
		}

		try {
			int localWidth = Integer.parseInt(width);
			if (localWidth > 144) {
				throw new RSSpectException(
						"width cannot be greater than 144px.");
			}
		} catch (NumberFormatException n) {
			throw new RSSpectException("invalid number format for width.");
		}

		this.width = width;
	}

	Width(Width width) {
		this.width = width.width;
	}

	/**
	 * @return the width.
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Shows the contents of the &lt;width> element.
	 */
	@Override
	public String toString() {
		return "<width>" + width + "</width>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Width)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
