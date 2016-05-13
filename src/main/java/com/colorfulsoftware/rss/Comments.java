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
 * The &lt;comments> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * URL of a page for comments relating to the item. <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#ltcommentsgtSubelementOfLtitemgt"
 * >More</a>.
 * </p>
 * 
 * <p>
 * &lt;comments> is an optional sub-element of &lt;item>.
 * </p>
 * 
 * <p>
 * If present, it is the url of the comments page for the item.
 * </p>
 * 
 * <p>
 * &lt;comments>http://ekzemplo.com/entry/4403/comments&lt;/comments>
 * </p>
 * 
 * <p>
 * More about comments <a
 * href="http://cyber.law.harvard.edu/rss/weblogComments.html">here</a>.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Comments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2013605887767981438L;

	private final String comments;

	Comments(String comments) throws RSSpectException {
		if (comments == null || comments.equals("")) {
			throw new RSSpectException("comments SHOULD NOT be blank.");
		}
		this.comments = comments;
	}

	Comments(Comments comments) {
		this.comments = comments.comments;
	}

	/**
	 * @return the comments url.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Shows the contents of the &lt;comments> element.
	 */
	@Override
	public String toString() {
		return "<comments>" + comments + "</comments>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Comments)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
