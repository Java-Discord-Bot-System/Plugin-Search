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
import java.util.Date;

/**
 * <p>
 * The &lt;lastBuildDate> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The last time the content of the channel changed.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class LastBuildDate implements Serializable {

	private static final long serialVersionUID = -8692371191911347659L;

	private final RSSDateConstruct lastBuildDate;

	LastBuildDate(String lastBuildDate) throws RSSpectException {
		this.lastBuildDate = new RSSDateConstruct(lastBuildDate);
	}

	LastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = new RSSDateConstruct(lastBuildDate);
	}

	/**
	 * 
	 * @return the date timestamp for this element.
	 */
	public Date getDateTime() {
		return lastBuildDate.getDateTime();
	}

	/**
	 * 
	 * @return the string formated version of the time for example
	 *         2006-04-28T12:50:43.337-05:00
	 */
	public String getText() {
		return lastBuildDate.getText();
	}

	/**
	 * Shows the contents of the &lt;lastBuildDate> element.
	 */
	@Override
	public String toString() {
		return "<lastBuildDate>" + lastBuildDate + "</lastBuildDate>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof LastBuildDate)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
