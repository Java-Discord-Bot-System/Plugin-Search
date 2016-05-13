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
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * The &lt;skipDays> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A hint for aggregators telling them which days they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skipdays">here</a>.
 * </p>
 * 
 * <p>
 * An XML element that contains up to seven &lt;day> sub-elements whose value is
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday. Aggregators
 * may not read the channel during days listed in the &lt;skipDays> element.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class SkipDays implements Serializable {

	private static final long serialVersionUID = -2480844569809695010L;

	private final List<Day> skipDays;

	SkipDays(List<Day> skipDays) throws RSSpectException {
		if (skipDays == null || skipDays.size() == 0) {
			throw new RSSpectException(
					"skipDays elements should contain at least one <day> sub element.");
		}
		this.skipDays = new LinkedList<Day>();
		for (Day day : skipDays) {
			this.skipDays.add(new Day(day));
		}
	}

	SkipDays(SkipDays skipDays) {
		this.skipDays = skipDays.getSkipDays();
	}

	/**
	 * @return the list of days to skip.
	 */
	public List<Day> getSkipDays() {
		List<Day> skipDaysCopy = new LinkedList<Day>();
		for (Day day : this.skipDays) {
			skipDaysCopy.add(new Day(day));
		}
		return skipDaysCopy;
	}

	/**
	 * @param skipDay
	 *            the day of the week.
	 * @return the day object of null if not found.
	 */
	public Day getSkipDay(String skipDay) {
		for (Day day : this.skipDays) {
			if (day.getDay().equals(skipDay)) {
				return new Day(day);
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;skipDays> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<skipDays>");
		for (Day day : this.skipDays) {
			sb.append(day);
		}
		sb.append("</skipDays>");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SkipDays)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
