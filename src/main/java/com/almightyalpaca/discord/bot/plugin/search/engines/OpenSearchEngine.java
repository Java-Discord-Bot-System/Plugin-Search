package com.almightyalpaca.discord.bot.plugin.search.engines;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

public abstract class OpenSearchEngine extends Engine {

	private final String url;

	public OpenSearchEngine(final String url) {
		super();
		this.url = url;
	}

	protected String getPage(final String string) throws IOException {
		try {
			return IOUtils.toString(new URL(this.url.replace("{searchTerms}", URLEncoder.encode(string, "UTF-8"))), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			System.out.println("WTF just happened???");
			throw e;
		}
	}

}
