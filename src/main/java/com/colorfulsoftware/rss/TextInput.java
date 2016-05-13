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
 * The &lt;textInput> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Specifies a text input box that can be displayed with the channel. More info
 * <a href=
 * "http://cyber.law.harvard.edu/rss/rss.html#lttextinputgtSubelementOfLtchannelgt"
 * >here</a>.
 * </p>
 * 
 * <p>
 * A channel may optionally contain a &lt;textInput> sub-element, which contains
 * four required sub-elements.
 * </p>
 * 
 * <p>
 * &lt;title> -- The label of the Submit button in the text input area.
 * </p>
 * 
 * <p>
 * &lt;description> -- Explains the text input area.
 * </p>
 * 
 * <p>
 * &lt;name> -- The name of the text object in the text input area.
 * </p>
 * 
 * <p>
 * <&lt;link> -- The URL of the CGI script that processes text input requests.
 * </p>
 * 
 * <p>
 * The purpose of the &lt;textInput> element is something of a mystery. You can
 * use it to specify a search engine box. Or to allow a reader to provide
 * feedback. Most aggregators ignore it.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class TextInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1063471668352135813L;

	private final Title title;
	private final Description description;
	private final Name name;
	private final Link link;

	TextInput(Title title, Description description, Name name, Link link)
			throws RSSpectException {
		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a title element.");
		}
		this.title = new Title(title.getTitle());

		// make sure description is present
		if (description == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a description element.");
		}
		this.description = new Description(description.getDescription());

		// make sure name is present
		if (name == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a name element.");
		}
		this.name = new Name(name.getName());

		// make sure link is present
		if (link == null) {
			throw new RSSpectException(
					"textInput elements MUST contain a link element.");
		}
		this.link = new Link(link.getLink());
		;
	}

	TextInput(TextInput textInput) {
		this.title = textInput.getTitle();
		this.description = textInput.getDescription();
		this.name = textInput.getName();
		this.link = textInput.getLink();
	}

	/**
	 * @return the title object.
	 */
	public Title getTitle() {
		return new Title(title);
	}

	/**
	 * @return the description object.
	 */
	public Description getDescription() {
		return new Description(description.getDescription());
	}

	/**
	 * @return the name object.
	 */
	public Name getName() {
		return new Name(name);
	}

	/**
	 * @return the link object.
	 */
	public Link getLink() {
		return new Link(link);

	}

	/**
	 * Shows the contents of the &lt;textInput> element.
	 */
	@Override
	public String toString() {
		return "<textInput>" + title + description + name + link
				+ "</textInput>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TextInput)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
