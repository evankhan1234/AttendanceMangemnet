package xact.idea.attendancesystem.Utils;


public class Constant {

    public static final String DOMAIN = "https://webhawks.oceanize.co.jp/PreMo-Api/public/";
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
