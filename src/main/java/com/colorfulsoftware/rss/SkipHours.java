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
 * The &lt;skipHours> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A hint for aggregators telling them which hours they can skip. More info <a
 * href
 * ="http://cyber.law.harvard.edu/rss/skipHoursDays.html#skiphours">here</a>.
 * </p>
 * 
 * <p>
 * skipHours
 * </p>
 * 
 * <p>
 * An XML element that contains up to 24 &lt;hour> sub-elements whose value is a
 * number between 0 and 23, representing a time in GMT, when aggregators, if
 * they support the feature, may not read the channel on hours listed in the
 * &lt;skipHours> element.
 * </p>
 * 
 * 
 * The hour beginning at midnight is hour zero.
 * 
 * @author Bill Brown
 * 
 */
public class SkipHours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7435503230975016475L;

	private final List<Hour> skipHours;

	SkipHours(List<Hour> skipHours) throws RSSpectException {
		if (skipHours == null || skipHours.size() == 0) {
			throw new RSSpectException(
					"skipHours elements should contain at least one <hour> sub element.");
		}
		this.skipHours = new LinkedList<Hour>();
		for (Hour hour : skipHours) {
			this.skipHours.add(new Hour(hour));
		}
	}

	SkipHours(SkipHours skipHours) {
		this.skipHours = skipHours.getSkipHours();
	}

	/**
	 * @return the list of hours to skip.
	 */
	public List<Hour> getSkipHours() {
		List<Hour> skipHoursCopy = new LinkedList<Hour>();
		for (Hour hour : this.skipHours) {
			skipHoursCopy.add(new Hour(hour));
		}
		return skipHoursCopy;
	}

	/**
	 * @param skipHour
	 *            the hour of the day.
	 * @return the hour object of null if not found.
	 */
	public Hour getSkipHour(String skipHour) {
		for (Hour hour : this.skipHours) {
			if (hour.getHour().equals(skipHour)) {
				return new Hour(hour);
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;skipHours> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<skipHours>");
		for (Hour hour : this.skipHours) {
			sb.append(hour);
		}
		sb.append("</skipHours>");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof SkipHours)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
