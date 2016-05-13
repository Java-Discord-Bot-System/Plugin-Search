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
 * The &lt;day> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * This class represents the day sub element of the &lt;skipDays> element.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Day implements Serializable {

	private static final long serialVersionUID = 1428375851718959215L;

	private final String day;

	Day(String day) throws RSSpectException {
		if (day == null || day.equals("")) {
			throw new RSSpectException("day SHOULD NOT be blank.");
		}
		this.day = day;
		if (!this.day.equals("Monday") && !this.day.equals("Tuesday")
				&& !this.day.equals("Wednesday")
				&& !this.day.equals("Thursday") && !this.day.equals("Friday")
				&& !this.day.equals("Saturday") && !this.day.equals("Sunday")) {
			throw new RSSpectException(
					"day elements must have a value of Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.");
		}

	}

	Day(Day day) {
		this.day = day.day;
	}

	/**
	 * @return the day of week; Monday, Tuesday, Wednesday, Thursday, Friday,
	 *         Saturday or Sunday.
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Shows the contents of the &lt;day> element.
	 */
	@Override
	public String toString() {
		return "<day>" + day + "</day>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Day)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
