package com.almightyalpaca.discord.bot.plugin.search.engines;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.colorfulsoftware.rss.RSSDoc;

public class RSSOpenSearchEngine extends OpenSearchEngine {

	public RSSOpenSearchEngine(final String url) {
		super(url);
	}

	@Override
	public List<Result> get(final String string) {
		try {
			final String page = this.getPage(string);

			return new RSSDoc().readRSSToBean(page).getChannel().getItems().stream().map(i -> {
				try {
					return new Result(i.getTitle().getTitle(), new URL(i.getLink().getLink()), i.getDescription().getDescription());
				} catch (final Exception e) {
					e.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
