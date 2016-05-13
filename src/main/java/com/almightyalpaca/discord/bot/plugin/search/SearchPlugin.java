package com.almightyalpaca.discord.bot.plugin.search;

import java.net.URL;
import java.util.Iterator;

import com.almightyalpaca.discord.bot.plugin.search.engines.Engine;
import com.almightyalpaca.discord.bot.plugin.search.engines.RSSOpenSearchEngine;
import com.almightyalpaca.discord.bot.plugin.search.engines.Result;
import com.almightyalpaca.discord.bot.system.command.Category;
import com.almightyalpaca.discord.bot.system.command.Command;
import com.almightyalpaca.discord.bot.system.command.CommandHandler;
import com.almightyalpaca.discord.bot.system.command.arguments.special.Rest;
import com.almightyalpaca.discord.bot.system.config.Config;
import com.almightyalpaca.discord.bot.system.events.commands.CommandEvent;
import com.almightyalpaca.discord.bot.system.exception.PluginLoadingException;
import com.almightyalpaca.discord.bot.system.exception.PluginUnloadingException;
import com.almightyalpaca.discord.bot.system.plugins.Plugin;
import com.almightyalpaca.discord.bot.system.plugins.PluginInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.MessageBuilder.Formatting;

public class SearchPlugin extends Plugin {

	class SearchCommand extends Command {

		public SearchCommand() {
			super("search", Category.INFO, "Search the web", "");
		}

		@CommandHandler
		private void onCommand(final CommandEvent event, final String engine, final Rest tag) {
			final MessageBuilder builder = new MessageBuilder();
			try {
				final Result result = SearchPlugin.this.getEngineByName(engine).getFirst(tag.getString());
				if (result == null) {
					builder.appendString("No results found.");
				} else {
					builder.appendString(result.getTitle(), Formatting.BOLD).newLine();
					builder.appendString("<" + result.getUrl() + ">").newLine();
					builder.appendString(result.getDescription()).newLine();
				}
			} catch (final Exception e) {
				e.printStackTrace();
				builder.appendString("An error occurred.");
			}
			event.sendMessage(builder);
		}
	}

	class SearchEnginesCommand extends Command {

		public SearchEnginesCommand() {
			super("searchengines", Category.BOT_ADMIN, "Add new search engines", "");
		}

		@CommandHandler
		private void onCommand(final CommandEvent event, final String action) {
			if (action.equalsIgnoreCase("list")) {
				final MessageBuilder builder = new MessageBuilder();

				builder.appendString("The following search enigines are registered:", Formatting.BOLD).newLine();

				final Iterator<JsonElement> iterator = SearchPlugin.this.config.getJsonArray("engines", new JsonArray()).iterator();
				while (iterator.hasNext()) {
					final JsonObject object = iterator.next().getAsJsonObject();
					builder.appendString(object.get("name").getAsString() + "     " + object.get("url").getAsString()).newLine();
				}

				builder.send(event.getChannel());
			}
		}

		@CommandHandler
		private void onCommand(final CommandEvent event, final String action, final String name) {
			if (action.equalsIgnoreCase("remove")) {
				final Iterator<JsonElement> iterator = SearchPlugin.this.config.getJsonArray("engines", new JsonArray()).iterator();
				while (iterator.hasNext()) {
					final JsonObject object = iterator.next().getAsJsonObject();
					if (object.get("name").getAsString().equalsIgnoreCase(name)) {
						iterator.remove();
					}
				}
				SearchPlugin.this.config.save();
			}
		}

		@CommandHandler
		private void onCommand(final CommandEvent event, final String action, final String name, final URL url) {
			if (action.equalsIgnoreCase("add")) {
				final Iterator<JsonElement> iterator = SearchPlugin.this.config.getJsonArray("engines", new JsonArray()).iterator();
				while (iterator.hasNext()) {
					final JsonObject object = iterator.next().getAsJsonObject();
					if (object.get("name").getAsString().equalsIgnoreCase(name)) {
						iterator.remove();
					}
				}
				final JsonObject object = new JsonObject();
				object.addProperty("name", name);
				object.addProperty("url", url.toString());
				object.addProperty("type", "opensearch/rss");
				SearchPlugin.this.config.getJsonArray("engines", new JsonArray()).add(object);
				SearchPlugin.this.config.save();
			}
		}

	}

	private static final PluginInfo INFO = new PluginInfo("com.almightyalpaca.discord.bot.plugin.search", "1.0.0", "Almighty Alpaca", "Search Plugin", "Search the web");

	private Config config;

	public SearchPlugin() {
		super(SearchPlugin.INFO);
	}

	public Engine getEngineByName(final String name) {
		final Iterator<JsonElement> iterator = SearchPlugin.this.config.getJsonArray("engines", new JsonArray()).iterator();
		while (iterator.hasNext()) {
			final JsonObject object = iterator.next().getAsJsonObject();
			if (object.get("name").getAsString().equalsIgnoreCase(name)) {
				return new RSSOpenSearchEngine(object.get("url").getAsString());
			}
		}
		return null;
	}

	@Override
	public void load() throws PluginLoadingException {

		this.config = this.getPluginConfig();

		this.registerCommand(new SearchCommand());
		this.registerCommand(new SearchEnginesCommand());
	}

	@Override
	public void unload() throws PluginUnloadingException {}
}
