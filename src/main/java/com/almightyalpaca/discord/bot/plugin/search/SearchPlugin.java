package com.almightyalpaca.discord.bot.plugin.search;

import com.almightyalpaca.discord.bot.system.command.Command;
import com.almightyalpaca.discord.bot.system.command.CommandHandler;
import com.almightyalpaca.discord.bot.system.command.arguments.special.Rest;
import com.almightyalpaca.discord.bot.system.config.Config;
import com.almightyalpaca.discord.bot.system.events.commands.CommandEvent;
import com.almightyalpaca.discord.bot.system.exception.PluginLoadingException;
import com.almightyalpaca.discord.bot.system.exception.PluginUnloadingException;
import com.almightyalpaca.discord.bot.system.plugins.Plugin;
import com.almightyalpaca.discord.bot.system.plugins.PluginInfo;

import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.MessageBuilder.Formatting;

public class SearchPlugin extends Plugin {

	class SearchCommand extends Command {

		public SearchCommand() {
			super("search", "Search the web", "");
		}

		@CommandHandler(dm = true, guild = true)
		private void onCommand(final CommandEvent event, final Rest rest) {
			final MessageBuilder builder = new MessageBuilder();
			try {
				final Result result = SearchEngine.GOOGLE.quereyFirst(rest.getString());
				if (result == null) {
					builder.appendString("No results found.");
				} else {
					builder.appendString(result.getTitle(), Formatting.BOLD).newLine();
					builder.appendString("<" + result.getUrl() + ">").newLine();
					builder.appendString(result.getContent()).newLine();
				}
			} catch (final Exception e) {
				e.printStackTrace();
				builder.appendString("An error occurred.");
			}
			event.sendMessage(builder);

		}
	}

	private static final PluginInfo	INFO	= new PluginInfo("com.almightyalpaca.discord.bot.plugin.search", "1.0.0", "Almighty Alpaca", "Search Plugin", "Search the web");
	private Config					config;

	public SearchPlugin() {
		super(SearchPlugin.INFO);
	}

	@Override
	public void load() throws PluginLoadingException {

		this.config = this.getPluginConfig();

		this.registerCommand(new SearchCommand());

	}

	@Override
	public void unload() throws PluginUnloadingException {}
}
