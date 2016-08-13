package com.prototype.shane.shaneprototype.util;

public interface Const {
	String PROVIDER_TYPE_PREFIX_DIR = "vnd.android.cursor.dir/vnd.";
	String PROVIDER_TYPE_PREFIX_ITEM = "vnd.android.cursor.item/vnd.";

	String SHANE_LOG = "shaneTest";

	String URL_BASE = "https://api.flickr.com/";//separate domain for easy switching environment
	String API_KEY = "949e98778755d1982f537d56236bbb42";
	String FLICKR_SEARCH_URL = URL_BASE + "services/rest/?method=flickr.photos.search&api_key=" + API_KEY + "&text=shark&format=json&nojsoncallback=1&page=1&extras=url_t,url_c,url_l,url_o";

	String URL_BASE_WORD_SEARCH = "https://s3.amazonaws.com/";//separate domain for easy switching environment

	String URL_GAME_DATA = URL_BASE_WORD_SEARCH + "duolingo-data/s3/js2/find_challenges.txt";

	String CHAT_SERVER_URL = "http://chat.socket.io";
}
