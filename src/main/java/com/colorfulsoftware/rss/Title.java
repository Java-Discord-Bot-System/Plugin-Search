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
 * The &lt;title> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * For Channel: The name of the channel. It's how people refer to your service.
 * If you have an HTML website that contains the same information as your RSS
 * file, the title of your channel should be the same as the title of your
 * website.
 * </p>
 * 
 * <p>
 * For Item: The title of the item.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Title implements Serializable {

	private static final long serialVersionUID = -8102075738201739128L;

	private final String title;

	Title(String title) throws RSSpectException {
		if (title == null || title.equals("")) {
			throw new RSSpectException("title SHOULD NOT be blank.");
		}
		this.title = title;
	}

	Title(Title title) {
		this.title = title.title;
	}

	/**
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Shows the contents of the &lt;title> element.
	 */
	@Override
	public String toString() {
		return "<title>" + title + "</title>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Title)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
