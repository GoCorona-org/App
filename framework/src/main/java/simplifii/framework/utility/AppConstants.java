package simplifii.framework.utility;

public interface AppConstants {

    String YOUTUBE_DEVELOPER_KEY = "AIzaSyAO3xKXODLtStJgSbH-JcZwSSznZylbsqs";
    String DEF_REGULAR_FONT = "Campton_Medium.ttf";
    String APP_LINK = "https://drive.google.com/file/d/0B8wKJnD6sONHeXlUbm5pOTk4dGM/view?usp=sharing";
    String USER_TOKEN = "userToken";
    String URL_SHORTNER_KEY = "AIzaSyBaV9FY4E6P2s_aPzSqQv5QBnJ6sIojWQQ";
    int RESULT_OK = 1;

    interface ACTION {
        String OUR_NETWORK = "ourNetwork";
        String SOCIAL_BUZZ = "socialBuzz";
        String HOME = "Home";
        String CURRENT_ISSUE = "currentIssue";
        String GALLERY = "gallery";
        String VIDEO = "video";
        String EVENT = "event";
    }

    interface REQUEST_CODES {
        int REQ_DONE = 2;
        int LOGIN = 6;
        int REGISTER = 7;
        int VERIFY_MOBILE = 8;
    }

    interface TRANSACTION_TYPES {
        int RECEIVED = 1;
    }

    interface ACTION_TYPE {
        int SELECTED_LANGUAGE = 2;
        int SELECT_STATE = 3;
        int SELECT_DISTRICT = 4;
        int SELECT_CITY = 5;
        int SELECT_COUNTRY_CODE = 6;
        int IMAGE = 7;
        int VIDEO = 8;
        int TEXT = 9;
        int FB = 10;
        int YOUTUBE = 11;
        int SHARE = 12;
        int SELECT_CONSTITUENCY = 13;
    }

    interface DIALOG_TYPE {
        int SELECT_STATE = 1;
        int SELECT_DISTRICT = 2;
        int SELECT_CITY = 3;
        int SELECT_CONSTITUENCY = 4;
    }


    interface DEVICE_TYPE {
        String ANDROID = "Android";
        String IOS = "Ios";
    }

    interface VIEW_CODES {

        int NEW_EVENT_CRETED = 1;
        int MAIN_DATA = 2;
        int LANGUAGE_DATA = 3;
        int STATE_IN = 4;
        int DISTRICT = 5;
        int CITY = 6;
        int COUNTRY_CODE = 7;
        int HOME_FEED_VIDEO = 8;
        int HOME_FEED_IMAGE = 9;
        int HOME_FEED_YOUTUBE = 10;
        int HOME_FEED_TEXT = 11;
        int HOME_FEED_FB_IMAGE = 12;
        int HOME_FEED_INSTAGRAM = 13;
        int HOME_FEED_TWITTER_IMAGE = 14;
        int HOME_FEED_WEB = 15;
        int ISSUES_FEED_VIDEO = 16;
        int ISSUES_FEED_COLLAGE_IMAGE = 17;
        int ISSUES_FEED_YOUTUBE = 18;
        int ISSUES_FEED_IMAGE = 19;
        int ISSUES_FEED_TEXT = 20;
        int ISSUES_FEED_FB = 21;
        int ISSUES_FEED_INSTAGRAM = 23;
        int ISSUES_FEED_TWITTER = 24;
        int ISSUES_FEED_WEB = 25;
        int EVENT_FEED = 26;
        int HOME_FEED_FB_VIDEO = 27;
        int HOME_FEED_TWITTER_VIDEOS = 28;
        int GALLERY_FEED_IMAGE = 29;
        int VIDEOS = 30;
        int Constituency = 31;
    }

    interface STRING_CONST {
        String STATE = "STATE";
        String DISTRICT = "DISTRICT";
        String CITY = "CITY";
        String COUNTRY_CODE = "COUNTRY_CODE";
        String Constituency = "Constituency";
    }


    interface PARAMS {
        String LAT = "latitude";
        String LNG = "longitude";
    }

    interface VALIDATIONS {
        String EMPTY = "empty";
        String EMAIL = "email";
        String MOBILE = "mobile";
    }

    interface ERROR_CODES {

        int UNKNOWN_ERROR = 0;
        int NO_INTERNET_ERROR = 1;
        int NETWORK_SLOW_ERROR = 2;
        int URL_INVALID = 3;
        int DEVELOPMENT_ERROR = 4;
        String DATA_NOT_FOUND = "NOT_FOUND";
    }

    interface PAGE_URL {
//        String BASE_URL = "http://35.154.222.238:8080/pep";//debug server
        String BASE_URL = "http://13.71.7.44:8080/pep";//live server
        String USERS = BASE_URL + "/api/users";
        String FEEDS = BASE_URL + "/api/feedItems/";

        String LOCATIONS = BASE_URL + "/api/locations";
        String GET_STATES = LOCATIONS + "/states?countryId=";


        String GET_DISTRICTS = LOCATIONS + "/districts?stateId=";
        String GET_CITIES = LOCATIONS + "/cities?districtId=";
        String USER_REGISTER = USERS + "/register";
        String USER_LOGIN = USERS + "/login";
        String USER_LOGIN_ONLY_MOBILE = USERS + "/mobileLogin";
        String GET_HOME_FEEDS = FEEDS;
        String CHECK_USERNAME = USERS+"/isPresent";
        String GET_GALLERY_FEEDS = BASE_URL + "/api/property/gallery/";
        String GET_VIDEOS_FEEDS = BASE_URL + "/api/property/video/";
        String POST_VIEWS = BASE_URL + "/api/feedActivities";
        String SEND_USER_DEVICE_TOKEN = BASE_URL + "/api/deviceInfo/";
        String GET_CONTINTUENCY = BASE_URL + "/api/constituency/?districtId=";
        String DOWNLOAD_PDF= "http://59.179.28.13:8011/retrievePDF";
    }

    interface PREF_KEYS {

        String KEY_LOGIN = "IsUserLoggedIn";
        String USER_DATA = "userData";
        String QUESTIONS_DATA = "questionsData";
        String FEED_COUNT_TIME = "feedCountTime";
    }

    interface BUNDLE_KEYS {
        String KEY_SERIALIZABLE_OBJECT = "KEY_SERIALIZABLE_OBJECT";
        String FRAGMENT_TYPE = "FRAGMENT_TYPE";
        String EXTRA_BUNDLE = "bundle";
        String EXTRA_MODEL = "model";
        String YOUTUBE_CHANNEL_ID = "youtube_channel_id";
        String VIDEO_URL = "video_url";
        String TITLE = "title";
        String DESC = "desc";
        String TO_LOGIN = "toLogin";
        String MOBILE_NO = "mobileNo";
        String SHOW_TOOLBAR_BACK_BTN = "showBackBtnInToolbar";
    }

    interface TASK_CODES {
        int UPLOAD_IMAGE = 1;
        int USER_UPLOAD_FILE = 2;
        int CATEGORY_VENDOR = 3;
        int GET_STATES = 4;
        int GET_DISTRICTS = 5;
        int GET_CITIES = 6;
        int REGISTER = 7;
        int LOGIN = 8;
        int GET_FEED_HOME = 9;
        int GET_FEED_NETWORK = 10;
        int GET_FEED_ISSUES = 11;
        int GET_FEED_GALLERY = 12;
        int GET_FEED_VIDEO = 13;
        int USER_CHECK = 14;
        int GET_FEED_EVENT = 15;
        int COUNT_FEED_VIEWS = 16;
        int SEND_USER_DEVICE_TOKEN = 17;
        int GET_CONSTITUENCY = 18;
        int PDF_DOWNLOAD = 19;
    }

    interface LANGUAGE {
        String ENGLISH = "en";
        String PUNJABI = "pa";
    }

    interface MEDIA_TYPES {
        String IMAGE = "img";
        String AUDIO = "audio";
        String VIDEO = "video";
        String GIF = "gif";
    }

    interface FRAGMENT_TYPES {
        int LOGIN = 5;
        int VIDEO_DETAIL = 2;
        int IMAGE_DETAIL = 3;
        int ABOUT_US = 6;
        int YOUTUBE_DETAIL = 4;
        int OUR_LEGACY = 7;
        int OUR_OBJECTIVES = 8;
    }

    interface JSON_FILES {
        String folder = "json/";
        String PAST_CURRENT_ILLNESS = folder + "past_current_illness.json";
        String ALLERGIES = folder + "allergies.json";
        String FURTHER_ANALIST = folder + "further_anylysis.json";
        String SYSTEM_REVIEW = folder + "system_review.json";
        String MEDICINE = folder + "medicine.json";
        String DIAGNOSIS = folder + "diagnosis.json";
        String QUALIFICATION = folder + "qualifications.json";
    }

    interface RESULT_CODE {
        int RESULT_DONE = 1;
        int RESULT_OK = -1;
    }

    interface HOME {
        String DRAWERNAME = "HOME";
        int CLICKID = 1;
    }


    interface DONATE {
        String DRAWERNAME = "DONATE";
        int CLICKID = 2;
    }

    interface OUR_NETWORK {
        String DRAWERNAME = "OUR NETWORK";
        int CLICKID = 3;
    }

    interface SOCIAL_BUZZ {
        String DRAWERNAME = "SOCIAL BUZZ";
        int CLICKID = 4;
    }

    interface MANIFESTO {
        String DRAWERNAME = "MANIFESTO";
        int CLICKID = 5;
    }

    interface NOTIFICATIONS {
        String DRAWERNAME = "NOTIFICATIONS";
        int CLICKID = 6;
    }

    interface LOGIN {
        String DRAWERNAME = "LOGIN";
        int CLICKID = 7;
    }
}
