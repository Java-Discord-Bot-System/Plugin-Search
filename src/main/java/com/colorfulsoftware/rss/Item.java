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
 * The &lt;item> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * A channel may contain any number of &lt;item>s. An item may represent a
 * "story" -- much like a story in a newspaper or magazine; if so its
 * description is a synopsis of the story, and the link points to the full
 * story. An item may also be complete in itself, if so, the description
 * contains the text (entity-encoded HTML is allowed; see <a
 * href="http://cyber.law.harvard.edu/rss/encodingDescriptions.html"
 * >examples</a>), and the link and title may be omitted. All elements of an
 * item are optional, however at least one of title or description must be
 * present.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6856122030764773780L;
	/* one of the two of these is required */
	private final Title title;

	private final Description description;

	/* optional elements */
	private final Link link;

	private final Author author;

	private final List<Category> categories;

	private final Comments comments;

	private final Enclosure enclosure;

	private final GUID guid;

	private final PubDate pubDate;

	private final Source source;

	private final List<Extension> extensions;

	private List<String> unboundPrefixes;

	Item(Title title, Link link, Description description, Author author,
			List<Category> categories, Comments comments, Enclosure enclosure,
			GUID guid, PubDate pubDate, Source source,
			List<Extension> extensions) throws RSSpectException {

		// make sure title or description is present
		if (title == null && description == null) {
			throw new RSSpectException(
					"item elements MUST contain either a title or description element.");
		}
		this.title = (title == null) ? null : new Title(title.getTitle());

		this.link = (link == null) ? null : new Link(link);

		this.description = (description == null) ? null : new Description(
				description.getDescription());

		this.author = (author == null) ? null : new Author(author.getAuthor());

		if (categories == null) {
			this.categories = null;
		} else {
			this.categories = new LinkedList<Category>();
			for (Category category : categories) {
				this.categories.add(new Category(category));
			}
		}

		this.comments = (comments == null) ? null : new Comments(comments
				.getComments());

		this.enclosure = (enclosure == null) ? null : new Enclosure(enclosure);

		this.guid = (guid == null) ? null : new GUID(guid.getIsPermaLink(),
				guid.getGuid());

		this.pubDate = (pubDate == null) ? null : new PubDate(pubDate
				.getDateTime());

		this.source = (source == null) ? null : new Source(source);

		// check that the extension prefixes are bound to a namespace
		this.unboundPrefixes = new LinkedList<String>();

		if (extensions == null) {
			this.extensions = null;
		} else {
			this.extensions = new LinkedList<Extension>();

			for (Extension extension : extensions) {
				// check that the extension prefix is bound to a namespace
				String namespacePrefix = extension.getNamespacePrefix();
				if (namespacePrefix != null) {
					this.unboundPrefixes.add(namespacePrefix);
				}
				this.extensions.add(new Extension(extension));
			}
		}

		this.unboundPrefixes = (this.unboundPrefixes.size() == 0) ? null
				: this.unboundPrefixes;
	}

	Item(Item item) {
		this.title = item.getTitle();
		this.description = item.getDescription();
		this.link = item.getLink();
		this.author = item.getAuthor();
		this.categories = item.getCategories();
		this.comments = item.getComments();
		this.enclosure = item.getEnclosure();
		this.guid = item.getGuid();
		this.pubDate = item.getPubDate();
		this.source = item.getSource();
		this.extensions = item.getExtensions();
	}

	/**
	 * @return the title object.
	 */
	public Title getTitle() {
		return (title == null) ? null : new Title(title);
	}

	/**
	 * @return the description object.
	 */
	public Description getDescription() {
		return (description == null) ? null : new Description(description
				.getDescription());
	}

	/**
	 * @return the link object.
	 */
	public Link getLink() {
		return (link == null) ? null : new Link(link);
	}

	/**
	 * @return the author object.
	 */
	public Author getAuthor() {
		return (author == null) ? null : new Author(author);
	}

	/**
	 * @return the list of categories.
	 */
	public List<Category> getCategories() {
		if (categories == null) {
			return null;
		} else {
			List<Category> catsCopy = new LinkedList<Category>();
			for (Category category : this.categories) {
				catsCopy.add(new Category(category));
			}
			return catsCopy;
		}
	}

	/**
	 * @return the comments object.
	 */
	public Comments getComments() {
		return (comments == null) ? null : new Comments(comments);
	}

	/**
	 * @return the enclosure object.
	 */
	public Enclosure getEnclosure() {
		return (enclosure == null) ? null : new Enclosure(enclosure);
	}

	/**
	 * @return the guid object.
	 */
	public GUID getGuid() {
		return (guid == null) ? null : new GUID(guid);
	}

	/**
	 * @return the published date object.
	 */
	public PubDate getPubDate() {
		return (pubDate == null) ? null : new PubDate(pubDate.getDateTime());
	}

	/**
	 * @return the source object.
	 */
	public Source getSource() {
		return (source == null) ? null : new Source(source);
	}

	/**
	 * 
	 * @return the list of extensions for this entry.
	 */
	public List<Extension> getExtensions() {
		if (extensions == null) {
			return null;
		}
		List<Extension> extsCopy = new LinkedList<Extension>();
		for (Extension extension : this.extensions) {
			extsCopy.add(new Extension(extension));
		}
		return extsCopy;
	}

	/**
	 * @param catValue
	 *            the value for the category.
	 * @return the category object if found otherwise null.
	 */
	public Category getCategory(String catValue) {
		if (this.categories != null) {
			for (Category category : this.categories) {
				if (category.getCategory() != null
						&& category.getCategory().equals(catValue)) {
					return new Category(category);
				}
			}
		}
		return null;
	}

	/**
	 * @param extName
	 *            the element name of the extension. eg. "atom:link" or
	 *            "someExtension"
	 * @return the extension matching the element or null if not found.
	 */
	public Extension getExtension(String extName) {
		if (this.extensions != null) {
			for (Extension extension : this.extensions) {
				if (extension.getElementName().equals(extName)) {
					return new Extension(extension);
				}
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;item> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<item>");

		if (title != null) {
			sb.append(title);
		}

		if (description != null) {
			sb.append(description);
		}

		if (link != null) {
			sb.append(link);
		}

		if (author != null) {
			sb.append(author);
		}

		if (categories != null) {
			for (Category category : categories) {
				sb.append(category);
			}
		}

		if (comments != null) {
			sb.append(comments);
		}

		if (enclosure != null) {
			sb.append(enclosure);
		}

		if (guid != null) {
			sb.append(guid);
		}

		if (pubDate != null) {
			sb.append(pubDate);
		}

		if (source != null) {
			sb.append(source);
		}

		if (extensions != null) {
			for (Extension extension : extensions) {
				sb.append(extension);
			}
		}

		sb.append("</item>");
		return sb.toString();
	}

	List<String> getUnboundPrefixes() {
		return unboundPrefixes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
