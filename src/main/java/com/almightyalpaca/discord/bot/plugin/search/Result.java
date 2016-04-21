package com.almightyalpaca.discord.bot.plugin.search;

import java.net.URL;

public class Result {

	private final String title;

	private final URL url;

	private final String content;

	public Result(final String title, final URL url, final String content) {
		this.title = title;
		this.url = url;
		this.content = content;
	}

	public final String getContent() {
		return this.content;
	}

	public final String getTitle() {
		return this.title;
	}

	public final URL getUrl() {
		return this.url;
	}

}
