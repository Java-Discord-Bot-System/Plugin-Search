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
 * The &lt;cloud> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Allows processes to register with a cloud to be notified of updates to the
 * channel, implementing a lightweight publish-subscribe protocol for RSS feeds.
 * More info &lt;a href= &quot;http://cyber.law.harvard.edu/rss/rss.html#
 * ltcloudgtSubelementOfLtchannelgt&quot; &gt;here&lt;/a&gt;
 * </p>
 * 
 * <p>
 * &lt;cloud&gt; sub-element of &lt;channel&gt;
 * </p>
 * 
 * <p>
 * &lt;cloud&gt; is an optional sub-element of &lt;channel&gt;.
 * </p>
 * 
 * <p>
 * It specifies a web service that supports the rssCloud interface which can be
 * implemented in HTTP-POST, XML-RPC or SOAP 1.1.
 * </p>
 * 
 * <p>
 * Its purpose is to allow processes to register with a cloud to be notified of
 * updates to the channel, implementing a lightweight publish-subscribe protocol
 * for RSS feeds.
 * </p>
 * 
 * <p>
 * &lt;cloud domain=&quot;rpc.sys.com&quot; port=&quot;80&quot;
 * path=&quot;/RPC2&quot; registerProcedure=&quot;myCloud.rssPleaseNotify&quot;
 * protocol=&quot;xml-rpc&quot; /&gt;
 * </p>
 * 
 * <p>
 * In this example, to request notification on the channel it appears in, you
 * would send an XML-RPC message to rpc.sys.com on port 80, with a path of
 * /RPC2. The procedure to call is myCloud.rssPleaseNotify.
 * </p>
 * 
 * <p>
 * A full explanation of this element and the rssCloud interface is <a
 * href=&quot
 * ;http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface&quot;
 * &gt;here&lt;/a&gt;.
 * </p>
 * 
 * 
 * @author Bill Brown
 * 
 */
public class Cloud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4431999134564899474L;

	private final List<Attribute> attributes;
	private final Attribute domain;
	private final Attribute port;
	private final Attribute path;
	private final Attribute registerProcedure;
	private final Attribute protocol;

	Cloud(List<Attribute> attributes) throws RSSpectException {
		if (attributes == null) {
			throw new RSSpectException(
					"The cloud element requires attributes:  See \"http://cyber.law.harvard.edu/rss/soapMeetsRss.html#rsscloudInterface\".");
		} else {
			this.attributes = new LinkedList<Attribute>();
			for (Attribute attr : attributes) {
				// check for unsupported attribute.
				this.attributes.add(new Attribute(attr.getName(), attr
						.getValue()));
			}
		}

		if ((this.domain = getAttribute("domain")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a domain attribute.");
		}

		if ((this.port = getAttribute("port")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a port attribute.");
		}

		if ((this.path = getAttribute("path")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a path attribute.");
		}

		if ((this.registerProcedure = getAttribute("registerProcedure")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a registerProcedure attribute.");
		}

		if ((this.protocol = getAttribute("protocol")) == null) {
			throw new RSSpectException(
					"cloud elements MUST have a protocol attribute.");
		}
		if (!getAttribute("protocol").getValue().equals("xml-rpc")
				&& !getAttribute("protocol").getValue().equals("soap")) {
			throw new RSSpectException(
					"the cloud's protocol attribute must be 'xml-rpc' or 'soap', case-sensitive.");
		}
	}

	Cloud(Cloud cloud) {
		this.attributes = cloud.getAttributes();
		this.domain = cloud.getDomain();
		this.port = cloud.getPort();
		this.path = cloud.getPath();
		this.registerProcedure = cloud.getRegisterProcedure();
		this.protocol = cloud.getProtocol();
	}

	/**
	 * 
	 * @return the cloud attribute list.
	 */
	public List<Attribute> getAttributes() {

		List<Attribute> attrsCopy = new LinkedList<Attribute>();
		for (Attribute attr : this.attributes) {
			attrsCopy.add(new Attribute(attr));
		}
		return attrsCopy;
	}

	/**
	 * @return the domain attribute.
	 */
	public Attribute getDomain() {
		return new Attribute(domain);
	}

	/**
	 * @return the port attribute
	 */
	public Attribute getPort() {
		return new Attribute(port);
	}

	/**
	 * @return the path attribute
	 */
	public Attribute getPath() {
		return new Attribute(path);
	}

	/**
	 * @return the registerProcedure attribute.
	 */
	public Attribute getRegisterProcedure() {
		return new Attribute(registerProcedure);
	}

	/**
	 * @return the protocol attribute.
	 */
	public Attribute getProtocol() {
		return new Attribute(protocol);
	}

	/**
	 * @param attrName
	 *            the name of the attribute to get.
	 * @return the Attribute object if attrName matches or null if not found.
	 */
	public Attribute getAttribute(String attrName) {
		for (Attribute attribute : this.attributes) {
			if (attribute.getName().equals(attrName)) {
				return new Attribute(attribute);
			}
		}
		return null;
	}

	/**
	 * Shows the contents of the &lt;cloud> element.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<cloud");
		for (Attribute attribute : attributes) {
			sb.append(attribute);
		}
		sb.append(" />");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Cloud)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}
	
	@Override public int hashCode() {
		return toString().hashCode();
	}
}
