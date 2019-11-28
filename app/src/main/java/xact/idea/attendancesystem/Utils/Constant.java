package xact.idea.attendancesystem.Utils;


public class Constant {
    public static final int SPLASH_TIME = 3000;
    public static final int TIME_OUT = 30000;
    public static final int LOADING_TIME = 1000;
    public static final String DOMAIN = "https://webhawks.oceanize.co.jp/PreMo-Api/public/";
    public static final int FRAG_HOME = 1;

    public static final int FRAG_SET_UP = 2;
    public static final int FRAG_USER_ACTIVTY= 3;

    public static final int FRAG_MORE = 4;
    public static final int FRAG_SET_UP_USER = 5;
    public static  String VALUE=null;
    public static  String SYNC;
    public static  String test;

    public static class API {
        public static final String LOGIN = DOMAIN + "users/login"; // done n

    }

    public static class Params {
        public static final String USER_ID = "User-Id";
        public static final String AUTHORIZATION = "Authorization";
        public static final String API_AUTH_DATE = "api_auth_date";
        public static final String API_AUTH_KEY = "api_auth_key";
        public static final String TEXT_BODY = "text_";
        public static final String FILE_BODY = "image_";
        public static final String API_RocketHome_AUTH_KEY = "RocketBaseKey";

    }

    public static class Val{
        public static final String VALUE_LOGIN = "login";
    }

}
