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
import java.util.List;

import javax.xml.stream.XMLStreamWriter;

/**
 * <p>
 * Used by the RSSDoc to write a RSS bean to a xml file or java String.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
class RSSWriter implements Serializable {

	private static final long serialVersionUID = -835487922633476364L;

	// used internally by FeedDoc to write feed to output streams.
	void writeRSS(XMLStreamWriter writer, RSS rss) throws Exception {

		// open the feed element
		writer.writeStartElement("rss");
		// the attributes should never be null since the version is required
		for (Attribute attr : rss.getAttributes()) {
			writer.writeAttribute(attr.getName(), attr.getValue());
		}

		// write the extensions
		if (rss.getExtensions() != null) {
			writeExtensions(writer, rss.getExtensions());
		}

		// write the channel
		// the channel should never be null since it is required
		writeChannel(writer, rss.getChannel());

		writer.writeEndElement();
	}

	void writeChannel(XMLStreamWriter writer, Channel channel) throws Exception {

		writer.writeStartElement("channel");

		// write the required elements
		writeTitle(writer, channel.getTitle());

		writeLink(writer, channel.getLink());

		writeDescription(writer, channel.getDescription());

		// write the optional elements

		if (channel.getLanguage() != null) {
			writeLanguage(writer, channel.getLanguage());
		}

		if (channel.getCopyright() != null) {
			writeCopyright(writer, channel.getCopyright());
		}

		if (channel.getManagingEditor() != null) {
			writeManagingEditor(writer, channel.getManagingEditor());
		}

		if (channel.getWebMaster() != null) {
			writeWebMaster(writer, channel.getWebMaster());
		}

		if (channel.getPubDate() != null) {
			writePubDate(writer, channel.getPubDate());
		}

		if (channel.getLastBuildDate() != null) {
			writeLastBuildDate(writer, channel.getLastBuildDate());
		}

		if (channel.getCategories() != null) {
			writeCategories(writer, channel.getCategories());
		}

		// always write out the rsspect library version.
		writeGenerator(writer, new RSSDoc().getLibVersion());

		if (channel.getDocs() != null) {
			writeDocs(writer, channel.getDocs());
		}

		if (channel.getCloud() != null) {
			writeCloud(writer, channel.getCloud());
		}

		if (channel.getTtl() != null) {
			writeTTL(writer, channel.getTtl());
		}

		if (channel.getImage() != null) {
			writeImage(writer, channel.getImage());
		}

		if (channel.getRating() != null) {
			writeRating(writer, channel.getRating());
		}

		if (channel.getTextInput() != null) {
			writeTextInput(writer, channel.getTextInput());
		}

		if (channel.getSkipHours() != null) {
			writeSkipHours(writer, channel.getSkipHours());
		}

		if (channel.getSkipDays() != null) {
			writeSkipDays(writer, channel.getSkipDays());
		}

		// add the extensions before the items.
		if (channel.getExtensions() != null) {
			writeExtensions(writer, channel.getExtensions());
		}

		// finally write the items
		if (channel.getItems() != null) {
			writeItems(writer, channel.getItems());
		}

		writer.writeEndElement();
	}

	void writeItems(XMLStreamWriter writer, List<Item> items) throws Exception {

		for (Item item : items) {

			writer.writeStartElement("item");

			// one of these two is required
			if (item.getTitle() != null) {
				writeTitle(writer, item.getTitle());
			}

			if (item.getDescription() != null) {
				writeDescription(writer, item.getDescription());
			}

			// write the optional elements
			if (item.getLink() != null) {
				writeLink(writer, item.getLink());
			}

			if (item.getAuthor() != null) {
				writeAuthor(writer, item.getAuthor());
			}

			if (item.getCategories() != null) {
				writeCategories(writer, item.getCategories());
			}

			if (item.getComments() != null) {
				writeComments(writer, item.getComments());
			}

			if (item.getEnclosure() != null) {
				writeEnclosure(writer, item.getEnclosure());
			}

			if (item.getGuid() != null) {
				writeGUID(writer, item.getGuid());
			}

			if (item.getPubDate() != null) {
				writePubDate(writer, item.getPubDate());
			}

			if (item.getSource() != null) {
				writeSource(writer, item.getSource());
			}

			if (item.getExtensions() != null) {
				writeExtensions(writer, item.getExtensions());
			}

			writer.writeEndElement();
		}

	}

	void writeAuthor(XMLStreamWriter writer, Author author) throws Exception {
		writer.writeStartElement("author");
		writer.writeCharacters(author.getAuthor());
		writer.writeEndElement();
	}

	void writeCategories(XMLStreamWriter writer, List<Category> categories)
			throws Exception {

		for (Category category : categories) {

			writer.writeStartElement("category");

			if (category.getDomain() != null) {
				writer.writeAttribute(category.getDomain().getName(), category
						.getDomain().getValue());
			}
			writer.writeCharacters(category.getCategory());
			writer.writeEndElement();

		}
	}

	void writeCloud(XMLStreamWriter writer, Cloud cloud) throws Exception {
		writer.writeEmptyElement("cloud");
		// the attributes should never be null since they are required
		for (Attribute attr : cloud.getAttributes()) {
			writer.writeAttribute(attr.getName(), attr.getValue());
		}
	}

	void writeComments(XMLStreamWriter writer, Comments comments)
			throws Exception {
		writer.writeStartElement("comments");
		writer.writeCharacters(comments.getComments());
		writer.writeEndElement();
	}

	void writeCopyright(XMLStreamWriter writer, Copyright copyright)
			throws Exception {
		writer.writeStartElement("copyright");
		writer.writeCharacters(copyright.getCopyright());
		writer.writeEndElement();
	}

	void writeDescription(XMLStreamWriter writer, Description description)
			throws Exception {
		if (description.getDescription().trim().equals("")) {
			writer.writeEmptyElement("description");
		} else {
			writer.writeStartElement("description");
			writer.writeCharacters(description.getDescription());
			writer.writeEndElement();
		}
	}

	void writeDocs(XMLStreamWriter writer, Docs docs) throws Exception {
		writer.writeStartElement("docs");
		writer.writeCharacters(docs.getDocs());
		writer.writeEndElement();
	}

	void writeEnclosure(XMLStreamWriter writer, Enclosure enclosure)
			throws Exception {

		writer.writeEmptyElement("enclosure");
		// the attributes should never be null since they are required
		for (Attribute attr : enclosure.getAttributes()) {
			writer.writeAttribute(attr.getName(), attr.getValue());
		}
	}

	void writeExtensions(XMLStreamWriter writer, List<Extension> extensions)
			throws Exception {

		for (Extension extension : extensions) {

			// if there is no content, then
			// write an empty extension element.
			if (extension.getContent() == null
					|| extension.getContent().trim().equals("")) {
				String elementName = extension.getElementName();
				if (elementName.indexOf(":") == -1) {
					writer.writeEmptyElement(elementName);
				} else {
					String prefix = elementName.substring(0, elementName
							.indexOf(":"));
					String localName = elementName.substring(elementName
							.indexOf(":") + 1);
					writer.writeEmptyElement(prefix, localName, "");
				}
				if (extension.getAttributes() != null) {
					for (Attribute attr : extension.getAttributes()) {
						writer.writeAttribute(attr.getName(), attr.getValue());
					}
				}
			} else {
				String elementName = extension.getElementName();
				if (elementName.indexOf(":") == -1) {
					writer.writeStartElement(elementName);
				} else {
					String prefix = elementName.substring(0, elementName
							.indexOf(":"));
					String localName = elementName.substring(elementName
							.indexOf(":") + 1);
					writer.writeStartElement(prefix, localName, "");
				}
				if (extension.getAttributes() != null) {
					for (Attribute attr : extension.getAttributes()) {
						writer.writeAttribute(attr.getName(), attr.getValue());
					}
				}
				// add the content.
				writer.writeCharacters(extension.getContent());

				// close the element.
				writer.writeEndElement();
			}
		}
	}

	void writeGenerator(XMLStreamWriter writer, Generator generator)
			throws Exception {
		writer.writeStartElement("generator");
		writer.writeCharacters(generator.getGenerator());
		writer.writeEndElement();
	}

	void writeGUID(XMLStreamWriter writer, GUID guid) throws Exception {
		writer.writeStartElement("guid");

		if (guid.getIsPermaLink() != null) {
			writer.writeAttribute(guid.getIsPermaLink().getName(), guid
					.getIsPermaLink().getValue());
		}
		writer.writeCharacters(guid.getGuid());
		writer.writeEndElement();
	}

	void writeHeight(XMLStreamWriter writer, Height height) throws Exception {
		writer.writeStartElement("height");
		writer.writeCharacters(height.getHeight());
		writer.writeEndElement();
	}

	void writeImage(XMLStreamWriter writer, Image image) throws Exception {

		writer.writeStartElement("image");

		// write required elements
		writeURL(writer, image.getUrl());
		writeTitle(writer, image.getTitle());
		writeLink(writer, image.getLink());

		// write optional elements
		if (image.getWidth() != null) {
			writeWidth(writer, image.getWidth());
		}

		if (image.getHeight() != null) {
			writeHeight(writer, image.getHeight());
		}

		if (image.getDescription() != null) {
			writeDescription(writer, image.getDescription());
		}

		writer.writeEndElement();
	}

	void writeLanguage(XMLStreamWriter writer, Language language)
			throws Exception {
		writer.writeStartElement("language");
		writer.writeCharacters(language.getLanguage());
		writer.writeEndElement();
	}

	void writeLastBuildDate(XMLStreamWriter writer, LastBuildDate lastBuildDate)
			throws Exception {
		writer.writeStartElement("lastBuildDate");
		writer.writeCharacters(lastBuildDate.getText());
		writer.writeEndElement();
	}

	void writeLink(XMLStreamWriter writer, Link link) throws Exception {
		writer.writeStartElement("link");
		writer.writeCharacters(link.getLink());
		writer.writeEndElement();
	}

	void writeManagingEditor(XMLStreamWriter writer,
			ManagingEditor managingEditor) throws Exception {
		writer.writeStartElement("managingEditor");
		writer.writeCharacters(managingEditor.getManagingEditor());
		writer.writeEndElement();
	}

	void writeName(XMLStreamWriter writer, Name name) throws Exception {
		writer.writeStartElement("name");
		writer.writeCharacters(name.getName());
		writer.writeEndElement();
	}

	void writePubDate(XMLStreamWriter writer, PubDate pubDate) throws Exception {
		writer.writeStartElement("pubDate");
		writer.writeCharacters(pubDate.getText());
		writer.writeEndElement();
	}

	void writeRating(XMLStreamWriter writer, Rating rating) throws Exception {
		writer.writeStartElement("rating");
		writer.writeCharacters(rating.getRating());
		writer.writeEndElement();
	}

	void writeSkipDays(XMLStreamWriter writer, SkipDays skipDays)
			throws Exception {
		writer.writeStartElement("skipDays");
		writeDays(writer, skipDays.getSkipDays());
		writer.writeEndElement();
	}

	void writeSkipHours(XMLStreamWriter writer, SkipHours skipHours)
			throws Exception {
		writer.writeStartElement("skipHours");
		writeHours(writer, skipHours.getSkipHours());
		writer.writeEndElement();
	}

	void writeDays(XMLStreamWriter writer, List<Day> skipDays) throws Exception {
		for (Day day : skipDays) {
			writer.writeStartElement("day");
			writer.writeCharacters(day.getDay());
			writer.writeEndElement();
		}
	}

	void writeHours(XMLStreamWriter writer, List<Hour> skipHours)
			throws Exception {
		for (Hour hour : skipHours) {
			writer.writeStartElement("hour");
			writer.writeCharacters(hour.getHour());
			writer.writeEndElement();
		}
	}

	void writeSource(XMLStreamWriter writer, Source source) throws Exception {
		writer.writeStartElement("source");

		// the attributes should never be null since url is required
		writer.writeAttribute(source.getUrl().getName(), source.getUrl()
				.getValue());
		writer.writeCharacters(source.getSource());
		writer.writeEndElement();
	}

	void writeTextInput(XMLStreamWriter writer, TextInput textInput)
			throws Exception {

		writer.writeStartElement("textInput");
		writeTitle(writer, textInput.getTitle());
		writeDescription(writer, textInput.getDescription());
		writeName(writer, textInput.getName());
		writeLink(writer, textInput.getLink());
		writer.writeEndElement();

	}

	void writeTitle(XMLStreamWriter writer, Title title) throws Exception {
		writer.writeStartElement("title");
		writer.writeCharacters(title.getTitle());
		writer.writeEndElement();
	}

	void writeTTL(XMLStreamWriter writer, TTL ttl) throws Exception {
		writer.writeStartElement("ttl");
		writer.writeCharacters(ttl.getTtl());
		writer.writeEndElement();
	}

	void writeURL(XMLStreamWriter writer, URL url) throws Exception {
		writer.writeStartElement("url");
		writer.writeCharacters(url.getUrl());
		writer.writeEndElement();
	}

	void writeWebMaster(XMLStreamWriter writer, WebMaster webMaster)
			throws Exception {
		writer.writeStartElement("webMaster");
		writer.writeCharacters(webMaster.getWebMaster());
		writer.writeEndElement();
	}

	void writeWidth(XMLStreamWriter writer, Width width) throws Exception {
		writer.writeStartElement("width");
		writer.writeCharacters(width.getWidth());
		writer.writeEndElement();
	}
}