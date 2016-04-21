package com.almightyalpaca.discord.bot.plugin.search;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public enum SearchEngine {

	GOOGLE("Google", "google", "g") {
		@Override
		public List<Result> query(final String string) {
			final List<Result> results = new ArrayList<>();
			try {
				final JSONObject response = Unirest.get("https://ajax.googleapis.com/ajax/services/search/web?start=0&rsz=large&v=1.0&q=" + URLEncoder.encode(string, "UTF-8"))
					.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36").asJson().getBody().getObject();
				final JSONArray array = response.getJSONObject("responseData").getJSONArray("results");
				for (int i = 0; i < array.length(); i++) {
					final JSONObject result = array.getJSONObject(i);
					try {
						results.add(new Result(StringEscapeUtils.unescapeHtml4(result.getString("titleNoFormatting")), new URL(result.getString("unescapedUrl")),
							StringEscapeUtils.unescapeHtml4(result.getString("content")).replaceAll("\\<.*?>", "").replace("//n", "")));
					} catch (JSONException | MalformedURLException e) {
						e.printStackTrace();
					}
				}
			} catch (final UnirestException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return results;
		}
	};

	private final List<String> codes;

	private final String name;

	private SearchEngine(final String name, final String... codes) {
		this.name = name;
		this.codes = new ArrayList<>();
		for (final String code : codes) {
			this.codes.add(code.toLowerCase());
		}
	}

	public static SearchEngine getEngineByCode(String code) {
		code = code.toLowerCase();
		for (final SearchEngine engine : SearchEngine.values()) {
			if (engine.codes.contains(code)) {
				return engine;
			}
		}
		return null;
	}

	public List<String> getCodes() {
		return this.codes;
	}

	public String getName() {
		return this.name;
	}

	public Result quereyFirst(final String string) {
		final List<Result> results = this.query(string);
		if (results.size() <= 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	public abstract List<Result> query(String string);
}
