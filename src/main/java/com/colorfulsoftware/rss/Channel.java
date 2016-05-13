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
 * The &lt;channel> element.
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
 * contains the text (entity-encoded HTML is allowed; see examples), and the
 * link and title may be omitted. All elements of an item are optional, however
 * at least one of title or description must be present.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193654133883709775L;

	/* required fields */
	private final Title title;

	private final Link link;

	private final Description description;

	/* optional fields */
	private final Language language;

	private final Copyright copyright;

	private final ManagingEditor managingEditor;

	private final WebMaster webMaster;

	private final PubDate pubDate;

	private final LastBuildDate lastBuildDate;

	private final List<Category> categories;

	private final Generator generator;

	private final Docs docs;

	private final Cloud cloud;

	private final TTL ttl;

	private final Image image;

	private final Rating rating;

	private final TextInput textInput;

	private final SkipHours skipHours;

	private final SkipDays skipDays;

	private final List<Item> items;

	private final List<Extension> extensions;

	private List<String> unboundPrefixes;

	Channel(Title title, Link link, Description description, Language language,
			Copyright copyright, ManagingEditor managingEditor,
			WebMaster webMaster, PubDate pubDate, LastBuildDate lastBuildDate,
			List<Category> categories, Generator generator, Docs docs,
			Cloud cloud, TTL ttl, Image image, Rating rating,
			TextInput textInput, SkipHours skipHours, SkipDays skipDays,
			List<Extension> extensions, List<Item> items)
			throws RSSpectException {

		// make sure title is present
		if (title == null) {
			throw new RSSpectException(
					"channel elements MUST contain a title element.");
		}
		this.title = new Title(title);

		// make sure link is present
		if (link == null) {
			throw new RSSpectException(
					"channel elements MUST contain a link element.");
		}
		this.link = new Link(link);

		// make sure description is present
		if (description == null) {
			throw new RSSpectException(
					"channel elements MUST contain a description element.");
		}
		this.description = new Description(description);

		this.language = (language == null) ? null : new Language(language);
		this.copyright = (copyright == null) ? null : new Copyright(copyright);
		this.managingEditor = (managingEditor == null) ? null
				: new ManagingEditor(managingEditor);
		this.webMaster = (webMaster == null) ? null : new WebMaster(webMaster);
		this.pubDate = (pubDate == null) ? null : new PubDate(pubDate
				.getDateTime());
		this.lastBuildDate = (lastBuildDate == null) ? null
				: new LastBuildDate(lastBuildDate.getDateTime());
		if (categories == null) {
			this.categories = null;
		} else {
			this.categories = new LinkedList<Category>();
			for (Category category : categories) {
				this.categories.add(new Category(category));
			}
		}
		this.generator = (generator == null) ? null : new Generator(generator
				.getGenerator());
		this.docs = (docs == null) ? null : new Docs(docs.getDocs());
		this.cloud = (cloud == null) ? null : new Cloud(cloud);
		this.ttl = (ttl == null) ? null : new TTL(ttl.getTtl());
		this.image = (image == null) ? null : new Image(image);
		this.rating = (rating == null) ? null : new Rating(rating.getRating());
		this.textInput = (textInput == null) ? null : new TextInput(textInput);
		this.skipHours = (skipHours == null) ? null : new SkipHours(skipHours);
		this.skipDays = (skipDays == null) ? null : new SkipDays(skipDays);

		// check that the extension prefixes are bound to a namespace
		this.unboundPrefixes = new LinkedList<String>();

		if (items == null) {
			this.items = null;
		} else {
			this.items = new LinkedList<Item>();
			for (Item item : items) {
				// add any unbound prefixes to test.
				if (item.getUnboundPrefixes() != null) {
					this.unboundPrefixes.addAll(item.getUnboundPrefixes());
				}
				this.items.add(new Item(item));
			}
		}

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

	Channel(Channel channel) {
		this.title = channel.getTitle();
		this.link = channel.getLink();
		this.description = channel.getDescription();
		this.language = channel.getLanguage();
		this.copyright = channel.getCopyright();
		this.managingEditor = channel.getManagingEditor();
		this.webMaster = channel.getWebMaster();
		this.pubDate = channel.getPubDate();
		this.lastBuildDate = channel.getLastBuildDate();
		this.categories = channel.getCategories();
		this.generator = channel.getGenerator();
		this.docs = channel.getDocs();
		this.cloud = channel.getCloud();
		this.ttl = channel.getTtl();
		this.image = channel.getImage();
		this.rating = channel.getRating();
		this.textInput = channel.getTextInput();
		this.skipHours = channel.getSkipHours();
		this.skipDays = channel.getSkipDays();
		this.items = channel.getItems();
		this.extensions = channel.getExtensions();
		this.unboundPrefixes = channel.getUnboundPrefixes();
	}

	/**
	 * @return the title.
	 */
	public Title getTitle() {
		return new Title(title);
	}

	/**
	 * @return the link element.
	 */
	public Link getLink() {
		return new Link(link);
	}

	/**
	 * @return the description element.
	 */
	public Description getDescription() {
		return new Description(description.getDescription());
	}

	/**
	 * @return the language element.
	 */
	public Language getLanguage() {
		return (language == null) ? null : new Language(language);
	}

	/**
	 * @return the copyright element
	 */
	public Copyright getCopyright() {
		return (copyright == null) ? null : new Copyright(copyright);
	}

	/**
	 * @return the managing editor element.
	 */
	public ManagingEditor getManagingEditor() {
		return (managingEditor == null) ? null : new ManagingEditor(
				managingEditor);
	}

	/**
	 * @return the webmaster element.
	 */
	public WebMaster getWebMaster() {
		return (webMaster == null) ? null : new WebMaster(webMaster);
	}

	/**
	 * @return the pubDate element.
	 */
	public PubDate getPubDate() {
		return (pubDate == null) ? null : new PubDate(pubDate.getDateTime());
	}

	/**
	 * @return the last build date element.
	 */
	public LastBuildDate getLastBuildDate() {
		return (lastBuildDate == null) ? null : new LastBuildDate(lastBuildDate
				.getDateTime());
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
	 * @return the generator element.
	 */
	public Generator getGenerator() {
		return (generator == null) ? null : new Generator(generator);
	}

	/**
	 * @return the docs element.
	 */
	public Docs getDocs() {
		return (docs == null) ? null : new Docs(docs);
	}

	/**
	 * @return the cloud element.
	 */
	public Cloud getCloud() {
		return (cloud == null) ? null : new Cloud(cloud);
	}

	/**
	 * @return the ttl element.
	 */
	public TTL getTtl() {
		return (ttl == null) ? null : new TTL(ttl);
	}

	/**
	 * @return the image element.
	 */
	public Image getImage() {
		return (image == null) ? null : new Image(image);
	}

	/**
	 * @return the rating element.
	 */
	public Rating getRating() {
		return (rating == null) ? null : new Rating(rating);
	}

	/**
	 * @return the textInput element.
	 */
	public TextInput getTextInput() {
		return (textInput == null) ? null : new TextInput(textInput);
	}

	/**
	 * @return the skipHours element.
	 */
	public SkipHours getSkipHours() {
		return (skipHours == null) ? null : new SkipHours(skipHours);
	}

	/**
	 * @return the skipDays element.
	 */
	public SkipDays getSkipDays() {
		return (skipDays == null) ? null : new SkipDays(skipDays);
	}

	/**
	 * @return the list of items.
	 */
	public List<Item> getItems() {
		if (items == null) {
			return null;
		} else {
			List<Item> itemsCopy = new LinkedList<Item>();
			for (Item item : this.items) {
				itemsCopy.add(new Item(item));
			}
			return itemsCopy;
		}
	}

	/**
	 * 
	 * @return the extensions for this entry.
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
	 *            the value of the category.
	 * @return the category name matching this item or null if not found.
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
	 * @param titleOrDescription
	 *            the title or description data.
	 * @return the item with this title or description. returns null if not
	 *         found.
	 */
	public Item getItem(String titleOrDescription) {
		if (this.items != null) {
			for (Item item : this.items) {
				if ((item.getTitle() != null
						&& item.getTitle().getTitle() != null && item
						.getTitle().getTitle().equals(titleOrDescription))
						|| (item.getDescription() != null && item
								.getDescription().getDescription().equals(
										titleOrDescription))) {
					return new Item(item);
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
	 * Shows the contents of the &lt;channel> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<channel>");

		sb.append(title);

		sb.append(link);

		sb.append(description);

		if (language != null) {
			sb.append(language);
		}

		if (copyright != null) {
			sb.append(copyright);
		}

		if (managingEditor != null) {
			sb.append(managingEditor);
		}

		if (webMaster != null) {
			sb.append(webMaster);
		}

		if (pubDate != null) {
			sb.append(pubDate);
		}

		if (lastBuildDate != null) {
			sb.append(lastBuildDate);
		}

		if (categories != null) {
			for (Category category : categories) {
				sb.append(category);
			}
		}

		if (generator != null) {
			sb.append(generator);
		}

		if (docs != null) {
			sb.append(docs);
		}

		if (cloud != null) {
			sb.append(cloud);
		}

		if (ttl != null) {
			sb.append(ttl);
		}

		if (image != null) {
			sb.append(image);
		}

		if (rating != null) {
			sb.append(rating);
		}

		if (textInput != null) {
			sb.append(textInput);
		}

		if (skipHours != null) {
			sb.append(skipHours);
		}

		if (skipDays != null) {
			sb.append(skipDays);
		}

		if (items != null) {
			for (Item item : items) {
				sb.append(item);
			}
		}

		if (extensions != null) {
			for (Extension extension : extensions) {
				sb.append(extension);
			}
		}

		sb.append("</channel>");
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
		if (!(obj instanceof Channel)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
