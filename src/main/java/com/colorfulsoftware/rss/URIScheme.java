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
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * Registered <a href="http://www.iana.org/assignments/uri-schemes.html">Uniform
 * Resource Identifer (URI) Schemes</a>
 * </p>
 * 
 * @author Bill Brown
 * 
 */
class URIScheme implements Serializable {

	private static final long serialVersionUID = -3125832208350819540L;

	private Map<String, String> uriScheme = new HashMap<String, String>();

	URIScheme() {
		uriScheme.put("aaa", null);
		uriScheme.put("aaas", null);
		uriScheme.put("acap", null);
		uriScheme.put("cap", null);
		uriScheme.put("cid", null);
		uriScheme.put("crid", null);
		uriScheme.put("data", null);
		uriScheme.put("dav", null);
		uriScheme.put("dict", null);
		uriScheme.put("dns", null);
		uriScheme.put("fax", null);
		uriScheme.put("file", null);
		uriScheme.put("ftp", null);
		uriScheme.put("go", null);
		uriScheme.put("gopher", null);
		uriScheme.put("h323", null);
		uriScheme.put("http", null);
		uriScheme.put("https", null);
		uriScheme.put("iax", null);
		uriScheme.put("icap", null);
		uriScheme.put("im", null);
		uriScheme.put("imap", null);
		uriScheme.put("info", null);
		uriScheme.put("ipp", null);
		uriScheme.put("iris", null);
		uriScheme.put("iris.beep", null);
		uriScheme.put("iris.xpc", null);
		uriScheme.put("iris.xpcs", null);
		uriScheme.put("iris.lwz", null);
		uriScheme.put("ldap", null);
		uriScheme.put("mailto", null);
		uriScheme.put("mid", null);
		uriScheme.put("modem", null);
		uriScheme.put("msrp", null);
		uriScheme.put("msrps", null);
		uriScheme.put("mtqp", null);
		uriScheme.put("mupdate", null);
		uriScheme.put("news", null);
		uriScheme.put("nfs", null);
		uriScheme.put("nntp", null);
		uriScheme.put("opaquelocktoken", null);
		uriScheme.put("pop", null);
		uriScheme.put("pres", null);
		uriScheme.put("rtsp", null);
		uriScheme.put("service", null);
		uriScheme.put("shttp", null);
		uriScheme.put("sieve", null);
		uriScheme.put("sip", null);
		uriScheme.put("sips", null);
		uriScheme.put("snmp", null);
		uriScheme.put("soap.beep", null);
		uriScheme.put("soap.beeps", null);
		uriScheme.put("tag", null);
		uriScheme.put("tel", null);
		uriScheme.put("telnet", null);
		uriScheme.put("tftp", null);
		uriScheme.put("thismessage", null);
		uriScheme.put("tip", null);
		uriScheme.put("tv", null);
		uriScheme.put("urn", null);
		uriScheme.put("vemmi", null);
		uriScheme.put("xmlrpc.beep", null);
		uriScheme.put("xmlrpc.beeps", null);
		uriScheme.put("xmpp", null);
		uriScheme.put("z39.50r", null);
		uriScheme.put("z39.50s", null);
		uriScheme.put("afs", null);
		uriScheme.put("dtn", null);
		uriScheme.put("mailserver", null);
		uriScheme.put("pack", null);
		uriScheme.put("tn3270", null);
		uriScheme.put("prospero", null);
		uriScheme.put("snews", null);
		uriScheme.put("videotex", null);
		uriScheme.put("wais", null);
	}

	public boolean contains(String scheme) {
		return uriScheme.containsKey(scheme);
	}
}
