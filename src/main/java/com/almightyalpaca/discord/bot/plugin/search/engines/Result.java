package com.almightyalpaca.discord.bot.plugin.search.engines;

import java.net.URL;

public class Result {

	private final String title;

	private final URL url;

	private final String description;

	public Result(final String title, final URL url, final String description) {
		this.title = title;
		this.url = url;
		this.description = description;
	}

	public final String getDescription() {
		return this.description;
	}

	public final String getTitle() {
		return this.title;
	}

	public final URL getUrl() {
		return this.url;
	}

	@Override
	public String toString() {
		return "Result [title=" + this.title + ", url=" + this.url + ", description=" + this.description + "]";
	}

}
