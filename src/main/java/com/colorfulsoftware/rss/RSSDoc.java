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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 * <p>
 * This class reads and writes RSS documents to and from xml files, objects or
 * Strings. It contains all of the factory methods for building immutable copies
 * of the object elements.
 * </p>
 * 
 * <p>
 * Here are some examples of how to use the RSSpect library in your application:
 * <br />
 * <ul style="margin-left:15px;">
 * <li>Read a file from disk into an RSS bean.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">RSS myRSS = new RSSDoc().readRSSToBean(new File("/myPath/myRSS.xml");</code>
 * </li>
 * <li>Read a file from the web into an RSS bean.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">RSS myRSS = new RSSDoc().readRSSToBean(new URL("http://www.abcdefg.net/myRSS.xml");</code>
 * </li>
 * <li>Read an RSS bean into a String.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">String myRssStr = myRSS.toString();</code>
 * </li>
 * <li>Read an RSS bean into a formatted String.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">String myRssStr = new RSSDoc().readRSSToString(myRSS, "javanet.staxutils.IndentingXMLStreamWriter");</code>
 * </li>
 * <li>Write an RSS bean to disk.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">String myRssStr = new RSSDoc().writeRSSDoc(new File("/somewhere/myRSS.xml"), myRSS, "UTF-8", "1.0");</code>
 * </li>
 * <li>Write a formatted RSS bean to disk.<br />
 * <code style="margin-left:20px;padding-bottom:10px;font-style: italic;">String myRssStr = new RSSDoc().writeRSSDoc(new javanet.staxutils.IndentingXMLStreamWriter( XMLOutputFactory.newInstance().createXMLStreamWriter( new FileOutputStream("/somewhere/myRSS.xml"), "UTF-8")), myRSS, "UTF-8", "1.0");</code>
 * </li>
 * </ul>
 * 
 * 
 * @author Bill Brown
 * 
 */
public final class RSSDoc implements Serializable {

	private static final long serialVersionUID = 649162683570000798L;

	/**
	 * the default document encoding of "UTF-8"
	 */
	private String encoding = System.getProperty("file.encoding");

	/**
	 * the default XML version of "1.0"
	 */
	private String xmlVersion = "1.0";

	private Generator libVersion;

	private XMLInputFactory inputFactory;

	private List<ProcessingInstruction> processingInstructions;

	/**
	 * @throws Exception
	 *             if the rsspect.properties file cant be read.
	 * 
	 */
	public RSSDoc() throws Exception {
		Properties props = new Properties();
		props.load(RSSDoc.class.getResourceAsStream("/rsspect.properties"));
		String libUri = props.getProperty("uri");
		String libVersionStr = props.getProperty("version");

		libVersion = new Generator(libUri + " v" + libVersionStr);
		inputFactory = XMLInputFactory.newInstance();
		// this is done to help for parsing documents that have undeclared and
		// unescaped html or xhtml entities.
		inputFactory.setProperty(
				"javax.xml.stream.isReplacingEntityReferences", Boolean.FALSE);
	}

	/**
	 * @param processingInstructions
	 *            xml processing instructions.
	 * @throws Exception
	 *             if the library version information cannot be loaded from the
	 *             environment.
	 */
	public RSSDoc(List<ProcessingInstruction> processingInstructions)
			throws Exception {
		this();
		this.processingInstructions = processingInstructions;
	}

	class ProcessingInstruction implements Serializable {
		private final String target;
		private final String data;
		private static final long serialVersionUID = -4261298860522801834L;

		ProcessingInstruction(String target, String data) {
			this.target = target;
			this.data = data;
		}

		/**
		 * @return the target of the processing instruction
		 */
		public String getTarget() {
			return target;
		}

		/**
		 * @return the processing instruction data.
		 */
		public String getData() {
			return data;
		}
	}

	/**
	 * @return the RSSpect library version in the form of a generator element.
	 *         This element is output for all feeds that are generated by
	 *         RSSpect.
	 */
	public Generator getLibVersion() {
		return new Generator(libVersion);
	}

	/**
	 * 
	 * @param output
	 *            the target output stream for the rss document.
	 * @param rss
	 *            the rss object containing the content of the feed
	 * @param encoding
	 *            the file encoding (default is UTF-8)
	 * @param version
	 *            the xml version (default is 1.0)
	 * @throws Exception
	 *             thrown if the feed cannot be written to the output
	 */
	public void writeRSSDoc(OutputStream output, RSS rss, String encoding,
			String version) throws Exception {
		writeRSSOutput(rss, XMLOutputFactory.newInstance()
				.createXMLStreamWriter(output, encoding), encoding, version);

	}

	/**
	 * 
	 * @param file
	 *            the target output file for the document.
	 * @param rss
	 *            the rss object containing the content of the feed
	 * @param encoding
	 *            the file encoding (default is UTF-8)
	 * @param version
	 *            the xml version (default is 1.0)
	 * @throws Exception
	 *             thrown if the feed cannot be written to the output
	 */
	public void writeRSSDoc(File file, RSS rss, String encoding, String version)
			throws Exception {
		writeRSSOutput(rss, XMLOutputFactory.newInstance()
				.createXMLStreamWriter(new FileOutputStream(file), encoding),
				encoding, version);
	}

	/**
	 * For example: to pass the TXW
	 * com.sun.xml.txw2.output.IndentingXMLStreamWriter or the stax-utils
	 * javanet.staxutils.IndentingXMLStreamWriter for indented printing do this:
	 * 
	 * <pre>
	 * XmlStreamWriter writer = new IndentingXMLStreamWriter(XMLOutputFactory
	 * 		.newInstance().createXMLStreamWriter(
	 * 				new FileOutputStream(outputFilePath), encoding));
	 * RSSDoc.writeFeedDoc(writer, myFeed, null, null);
	 * </pre>
	 * 
	 * @param output
	 *            the target output for the feed.
	 * @param rss
	 *            the rss object containing the content of the feed
	 * @param encoding
	 *            the file encoding (default is UTF-8)
	 * @param version
	 *            the xml version (default is 1.0)
	 * @throws Exception
	 *             thrown if the feed cannot be written to the output
	 */
	public void writeRSSDoc(XMLStreamWriter output, RSS rss, String encoding,
			String version) throws Exception {
		writeRSSOutput(rss, output, encoding, version);
	}

	/**
	 * This method reads in a Feed element and returns the contents as an rss
	 * feed string with formatting specified by the fully qualified
	 * XMLStreamWriter class name (uses reflection internally). For example you
	 * can pass the TXW com.sun.xml.txw2.output.IndentingXMLStreamWriter or the
	 * stax-utils javanet.staxutils.IndentingXMLStreamWriter for indented
	 * printing. It will fall back to the feeds' toString() method if the
	 * xmlStreamWriter is not recognized.
	 * 
	 * if the XMLStreamWriter class cannot be found in the classpath.
	 * 
	 * @param rss
	 *            the rss object to be converted to an rss document string.
	 * @param xmlStreamWriter
	 *            the fully qualified XMLStreamWriter class name.
	 * @return an rss feed document string.
	 * @throws Exception
	 *             thrown if the feed cannot be returned as a String
	 */
	public String readRSSToString(RSS rss, String xmlStreamWriter)
			throws Exception {
		if (rss == null) {
			throw new RSSpectException("The rss feed object cannot be null.");
		}
		try {
			StringWriter theString = new StringWriter();
			if (xmlStreamWriter == null || xmlStreamWriter.equals("")) {
				writeRSSOutput(rss, XMLOutputFactory.newInstance()
						.createXMLStreamWriter(theString), encoding, xmlVersion);
			} else {
				Class<?> cls = Class.forName(xmlStreamWriter);
				Constructor<?> ct = cls
						.getConstructor(new Class[] { XMLStreamWriter.class });
				Object arglist[] = new Object[] { XMLOutputFactory
						.newInstance().createXMLStreamWriter(theString) };
				XMLStreamWriter writer = (XMLStreamWriter) ct
						.newInstance(arglist);

				writeRSSOutput(rss, writer, encoding, xmlVersion);
			}

			return theString.toString();

			// if the xmlStreamWriter cannot be found, return the default
		} catch (Exception e) {
			return rss.toString();
		}
	}

	/**
	 * This method reads an xml string into a Feed element.
	 * 
	 * @param xmlString
	 *            the xml string to be transformed into a RSS element.
	 * @return the RSS element
	 * @throws Exception
	 *             if the string cannot be parsed into a RSS element.
	 */
	public RSS readRSSToBean(String xmlString) throws Exception {
		// try to grab the encoding first:
		if (xmlString.contains("encoding=\"")) {
			String localEncoding = xmlString.substring(xmlString
					.indexOf("encoding=\"") + 10);
			localEncoding = localEncoding.substring(0, localEncoding
					.indexOf('"'));
			encoding = localEncoding;

		}
		return new RSSReader(this).readRSS(inputFactory
				.createXMLStreamReader(new ByteArrayInputStream(xmlString
						.getBytes(encoding))));
	}

	/**
	 * This method reads an xml File object into a Feed element.
	 * 
	 * @param file
	 *            the file object representing an rss feed.
	 * @return the RSS element.
	 * @throws Exception
	 *             if the file cannot be parsed into a RSS element.
	 */
	public RSS readRSSToBean(File file) throws Exception {
		return new RSSReader(this).readRSS(inputFactory
				.createXMLStreamReader(new FileInputStream(file)));
	}

	/**
	 * This method reads an rss file from a URL into a Feed element.
	 * 
	 * @param url
	 *            the Internet network location of an rss file.
	 * @return the RSS element.
	 * @throws Exception
	 *             if the URL cannot be parsed into a RSS element.
	 */
	public RSS readRSSToBean(java.net.URL url) throws Exception {
		return readRSSToBean(url.openStream());
	}

	/**
	 * This method reads an rss file from an input stream into a RSS element.
	 * 
	 * @param inputStream
	 *            the input stream containing an rss file.
	 * @return the RSS element.
	 * @throws Exception
	 *             if the URL cannot be parsed into a RSS element.
	 */
	public RSS readRSSToBean(InputStream inputStream) throws Exception {
		return new RSSReader(this).readRSS(inputFactory
				.createXMLStreamReader(inputStream));
	}

	/**
	 * 
	 * @param channel
	 *            the unique channel element (required)
	 * @param attributes
	 *            additional attributes (optional)
	 * @param extensions
	 *            additional extensions (optional)
	 * @return an immutable RSS object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public RSS buildRSS(Channel channel, List<Attribute> attributes,
			List<Extension> extensions) throws RSSpectException {
		return new RSS(channel, attributes, extensions);
	}

	/**
	 * 
	 * @param name
	 *            the attribute name.
	 * @param value
	 *            the attribute value.
	 * @return an immutable Attribute object.
	 * @throws RSSpectException
	 *             if the data is not valid.
	 */
	public Attribute buildAttribute(String name, String value)
			throws RSSpectException {
		return new Attribute(name, value);
	}

	/**
	 * 
	 * @param author
	 *            the author element. (required)
	 * @return an immutable Author object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Author buildAuthor(String author) throws RSSpectException {
		return new Author(author);
	}

	/**
	 * @param domain
	 *            the domain attribute
	 * @param category
	 *            the category text
	 * @return an immutable Category object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Category buildCategory(Attribute domain, String category)
			throws RSSpectException {
		return new Category(domain, category);
	}

	/**
	 * 
	 * @param title
	 *            the title element.
	 * @param link
	 *            the link element.
	 * @param description
	 *            the description element.
	 * @param language
	 *            the language element.
	 * @param copyright
	 *            the copyright element.
	 * @param managingEditor
	 *            the managingEditor element.
	 * @param webMaster
	 *            the webMaster element.
	 * @param pubDate
	 *            the pubDate element.
	 * @param lastBuildDate
	 *            the lastBuildDate element.
	 * @param categories
	 *            the list of categories
	 * @param generator
	 *            the generator element.
	 * @param docs
	 *            the docs element.
	 * @param cloud
	 *            the cloud element.
	 * @param ttl
	 *            the ttl element.
	 * @param image
	 *            the image element.
	 * @param rating
	 *            the rating element.
	 * @param textInput
	 *            the textInput element.
	 * @param skipHours
	 *            the skipHours element.
	 * @param skipDays
	 *            the skipDays element.
	 * @param items
	 *            the list of items.
	 * @param extensions
	 *            the list of extensions.
	 * @return an immutable Channel object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Channel buildChannel(Title title, Link link,
			Description description, Language language, Copyright copyright,
			ManagingEditor managingEditor, WebMaster webMaster,
			PubDate pubDate, LastBuildDate lastBuildDate,
			List<Category> categories, Generator generator, Docs docs,
			Cloud cloud, TTL ttl, Image image, Rating rating,
			TextInput textInput, SkipHours skipHours, SkipDays skipDays,
			List<Extension> extensions, List<Item> items)
			throws RSSpectException {
		return new Channel(title, link, description, language, copyright,
				managingEditor, webMaster, pubDate, lastBuildDate, categories,
				generator, docs, cloud, ttl, image, rating, textInput,
				skipHours, skipDays, extensions, items);
	}

	/**
	 * 
	 * @param attributes
	 *            the list of attributes.
	 * @return an immutable Cloud object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Cloud buildCloud(List<Attribute> attributes) throws RSSpectException {
		return new Cloud(attributes);
	}

	/**
	 * 
	 * @param comments
	 *            the comments.
	 * @return an immutable Comments object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Comments buildComments(String comments) throws RSSpectException {
		return new Comments(comments);
	}

	/**
	 * 
	 * @param copyright
	 *            the copyright.
	 * @return an immutable Copyright object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Copyright buildCopyright(String copyright) throws RSSpectException {
		return new Copyright(copyright);
	}

	/**
	 * 
	 * @param description
	 *            the description.
	 * @return an immutable Description object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Description buildDescription(String description)
			throws RSSpectException {
		return new Description(description);
	}

	/**
	 * 
	 * @param docs
	 *            the documentation information.
	 * @return an immutable Docs object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Docs buildDocs(String docs) throws RSSpectException {
		return new Docs(docs);
	}

	/**
	 * 
	 * @param attributes
	 *            should contain url, length and type
	 * @return an immutable Enclosure object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Enclosure buildEnclosure(List<Attribute> attributes)
			throws RSSpectException {
		return new Enclosure(attributes);
	}

	/**
	 * 
	 * @param elementName
	 *            the name of the extension element.
	 * @param attributes
	 *            additional attributes.
	 * @param content
	 *            the content of the extension element.
	 * @return an immutable Extension object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Extension buildExtension(String elementName,
			List<Attribute> attributes, String content) throws RSSpectException {
		return new Extension(elementName, attributes, content);
	}

	/**
	 * @param text
	 *            the text content.
	 * @return an immutable Generator object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Generator buildGenerator(String text) throws RSSpectException {
		return new Generator(text);
	}

	/**
	 * 
	 * @param isPermaLink
	 *            the isPermaLink attributes.
	 * @param guid
	 *            the guid data.
	 * @return an immutable GUID object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public GUID buildGUID(Attribute isPermaLink, String guid)
			throws RSSpectException {
		return new GUID(isPermaLink, guid);
	}

	/**
	 * 
	 * @param height
	 *            should be a number 400 or less
	 * @return an immutable Height object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Height buildHeight(String height) throws RSSpectException {
		return new Height(height);
	}

	/**
	 * 
	 * @param url
	 *            the url element.
	 * @param title
	 *            the title element.
	 * @param link
	 *            the link element.
	 * @param width
	 *            the width element.
	 * @param height
	 *            the height element.
	 * @param description
	 *            the description element.
	 * @return an immutable Image object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Image buildImage(URL url, Title title, Link link, Width width,
			Height height, Description description) throws RSSpectException {
		return new Image(url, title, link, width, height, description);
	}

	/**
	 * 
	 * @param title
	 *            the title element.
	 * @param link
	 *            the link element.
	 * @param description
	 *            the description element.
	 * @param author
	 *            the author element.
	 * @param categories
	 *            the list of categories.
	 * @param comments
	 *            the comments element.
	 * @param enclosure
	 *            the enclosure element.
	 * @param guid
	 *            the guid element.
	 * @param pubDate
	 *            the published date element.
	 * @param source
	 *            the source element.
	 * @param extensions
	 *            the list of extensions.
	 * @return an immutable Item object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Item buildItem(Title title, Link link, Description description,
			Author author, List<Category> categories, Comments comments,
			Enclosure enclosure, GUID guid, PubDate pubDate, Source source,
			List<Extension> extensions) throws RSSpectException {
		return new Item(title, link, description, author, categories, comments,
				enclosure, guid, pubDate, source, extensions);
	}

	/**
	 * 
	 * @param language
	 *            the language.
	 * @return an immutable Language object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Language buildLanguage(String language) throws RSSpectException {
		return new Language(language);
	}

	/**
	 * 
	 * @param lastBuildDate
	 *            the last build date.
	 * @return an immutable LastBuildDate object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public LastBuildDate buildLastBuildDate(String lastBuildDate)
			throws RSSpectException {
		return new LastBuildDate(lastBuildDate);
	}

	/**
	 * 
	 * @param link
	 *            the link information.
	 * @return an immutable Link object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Link buildLink(String link) throws RSSpectException {
		return new Link(link);
	}

	/**
	 * 
	 * @param managingEditor
	 *            the managing editor.
	 * @return an immutable ManagingEditor object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public ManagingEditor buildManagingEditor(String managingEditor)
			throws RSSpectException {
		return new ManagingEditor(managingEditor);
	}

	/**
	 * 
	 * @param name
	 *            the name.
	 * @return an immutable Name object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Name buildName(String name) throws RSSpectException {
		return new Name(name);
	}

	/**
	 * 
	 * @param pubDate
	 *            the published date.
	 * @return an immutable PubDate object.
	 * @throws RSSpectException
	 *             If the dateTime format is invalid.
	 */
	public PubDate buildPubDate(String pubDate) throws RSSpectException {
		return new PubDate(pubDate);
	}

	/**
	 * 
	 * @param rating
	 *            the rating information.
	 * @return an immutable Rating object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Rating buildRating(String rating) throws RSSpectException {
		return new Rating(rating);
	}

	/**
	 * 
	 * @param skipDays
	 *            the days to skip.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 * @return an immutable SkipDays object.
	 */
	public SkipDays buildSkipDays(List<Day> skipDays) throws RSSpectException {
		return new SkipDays(skipDays);
	}

	/**
	 * 
	 * @param skipHours
	 *            the hours to skip.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 * @return an immutable SkipHours object.
	 */
	public SkipHours buildSkipHours(List<Hour> skipHours)
			throws RSSpectException {
		return new SkipHours(skipHours);
	}

	/**
	 * @param day
	 *            the day of the week.
	 * @return a Day object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Day buildDay(String day) throws RSSpectException {
		return new Day(day);
	}

	/**
	 * @param hour
	 *            the hour of the day.
	 * @return an Hour object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Hour buildHour(String hour) throws RSSpectException {
		return new Hour(hour);
	}

	/**
	 * 
	 * @param url
	 *            the url attribute.
	 * @param source
	 *            the source information.
	 * @return an immutable Source object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Source buildSource(Attribute url, String source)
			throws RSSpectException {
		return new Source(url, source);
	}

	/**
	 * 
	 * @param title
	 *            the title element.
	 * @param description
	 *            the description element.
	 * @param name
	 *            the name element.
	 * @param link
	 *            the link element.
	 * @return an immutable TextInput object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public TextInput buildTextInput(Title title, Description description,
			Name name, Link link) throws RSSpectException {
		return new TextInput(title, description, name, link);
	}

	/**
	 * 
	 * @param title
	 *            the title.
	 * @return an immutable Title object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Title buildTitle(String title) throws RSSpectException {
		return new Title(title);
	}

	/**
	 * 
	 * @param ttl
	 *            the time to live.
	 * @return an immutable TTL object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public TTL buildTTL(String ttl) throws RSSpectException {
		return new TTL(ttl);
	}

	/**
	 * 
	 * @param url
	 *            the url.
	 * @return an immutable URL object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public URL buildURL(String url) throws RSSpectException {
		return new URL(url);
	}

	/**
	 * 
	 * @param webMaster
	 *            the web master.
	 * @return an immutable WebMaster object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public WebMaster buildWebMaster(String webMaster) throws RSSpectException {
		return new WebMaster(webMaster);
	}

	/**
	 * 
	 * @param width
	 *            the width.
	 * @return an immutable Width object.
	 * @throws RSSpectException
	 *             if the format of the data is not valid.
	 */
	public Width buildWidth(String width) throws RSSpectException {
		return new Width(width);
	}

	// used to write feed output for several feed writing methods.
	private void writeRSSOutput(RSS rss, XMLStreamWriter writer,
			String encoding, String version) throws Exception {

		if (rss == null) {
			throw new RSSpectException("The rss feed object cannot be null.");
		}

		Channel channel = rss.getChannel();

		rss = buildRSS(buildChannel(channel.getTitle(), channel.getLink(),
				channel.getDescription(), channel.getLanguage(), channel
						.getCopyright(), channel.getManagingEditor(), channel
						.getWebMaster(), channel.getPubDate(), channel
						.getLastBuildDate(), channel.getCategories(),
				getLibVersion(), channel.getDocs(), channel.getCloud(), channel
						.getTtl(), channel.getImage(), channel.getRating(),
				channel.getTextInput(), channel.getSkipHours(), channel
						.getSkipDays(), channel.getExtensions(), channel
						.getItems()), rss.getAttributes(), rss.getExtensions());

		// write the xml header.
		writer.writeStartDocument(encoding, version);
		if (this.processingInstructions != null) {
			for (ProcessingInstruction pi : this.processingInstructions) {
				writer.writeProcessingInstruction(pi.getTarget(), pi.getData());
			}
		}

		new RSSWriter().writeRSS(writer, rss);
		writer.flush();
		writer.close();
	}

	/**
	 * @return the xml encoding of the document eg. UTF-8
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @return the xml document version of the document eg. 1.0
	 */
	public String getXmlVersion() {
		return xmlVersion;
	}

	void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	void setProcessingInstructions(
			List<ProcessingInstruction> processingInstructions) {
		this.processingInstructions = processingInstructions;
	}

	List<ProcessingInstruction> getProcessingInstructions() {
		// TODO Auto-generated method stub
		return processingInstructions;
	}

}
