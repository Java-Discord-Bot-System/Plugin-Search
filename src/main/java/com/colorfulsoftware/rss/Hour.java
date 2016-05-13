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
 * The &lt;hour> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * This class returns the hour sub element of the &lt;skipHours> element. Valid
 * values are 0 - 23.
 * </p>
 * 
 * @author bill
 * 
 */
public class Hour implements Serializable {

	private static final long serialVersionUID = -6736105071042205154L;

	private final String hour;

	Hour(String hour) throws RSSpectException {

		if (hour == null || hour.equals("")) {
			throw new RSSpectException("hour SHOULD NOT be blank.");
		}

		try {
			int localHour = Integer.parseInt(hour);
			if (localHour > 23 || localHour < 0) {
				throw new RSSpectException(
						"hour elements must be between 0 and 23 inclusive.");
			}
		} catch (NumberFormatException n) {
			throw new RSSpectException("invalid number format for hour.");
		}

		this.hour = hour;
	}

	Hour(Hour hour) {
		this.hour = hour.hour;
	}

	/**
	 * @return the hour.
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * Shows the contents of the &lt;hour> element.
	 */
	@Override
	public String toString() {
		return "<hour>" + hour + "</hour>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Hour)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
