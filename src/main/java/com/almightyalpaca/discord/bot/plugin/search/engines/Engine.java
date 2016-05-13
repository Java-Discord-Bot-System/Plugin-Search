package com.almightyalpaca.discord.bot.plugin.search.engines;

import java.util.List;

public abstract class Engine {

	protected Engine() {}

	public abstract List<Result> get(final String string);

	public Result getFirst(final String string) {
		final List<Result> results = this.get(string);
		if (results == null || results.size() <= 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

}
