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
 * The &lt;pubDate> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * For Channel: The publication date for the content in the channel. For
 * example, the New York Times publishes on a daily basis, the publication date
 * flips once every 24 hours. That's when the pubDate of the channel changes.
 * All date-times in RSS conform to the Date and Time Specification of <a
 * href="http://asg.web.cmu.edu/rfc/rfc822.html">RFC 822</a>, with the exception
 * that the year may be expressed with two characters or four characters (four
 * preferred).
 * </p>
 * 
 * <p>
 * For Item: Indicates when the item was published. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltpubdategtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;pubDate> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * Its value is a <a href="http://asg.web.cmu.edu/rfc/rfc822.html">date</a>,
 * indicating when the item was published. If it's a date in the future,
 * aggregators may choose to not display the item until that date.
 * </p>
 * 
 * <p>
 * &lt;pubDate>Sun, 19 May 2002 15:21:36 GMT&lt;/pubDate>
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class PubDate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1876136443511726976L;

	private final RSSDateConstruct pubDate;

	PubDate(String pubDate) throws RSSpectException {
		this.pubDate = new RSSDateConstruct(pubDate);
	}

	PubDate(Date pubDate) {
		this.pubDate = new RSSDateConstruct(pubDate);
	}

	/**
	 * 
	 * @return the date timestamp for this element.
	 */
	public Date getDateTime() {
		return pubDate.getDateTime();
	}

	/**
	 * 
	 * @return the string formated version of the time for example
	 *         2006-04-28T12:50:43.337-05:00
	 */
	public String getText() {
		return pubDate.getText();
	}

	/**
	 * Shows the contents of the &lt;pubDate> element.
	 */
	@Override
	public String toString() {
		return "<pubDate>" + pubDate + "</pubDate>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PubDate)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
