package simplifii.framework.utility;

import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by nitin on 04/03/16.
 */
public class JsonUtil {
    private static AtomicReference<Gson> gsonAtomicReference = new AtomicReference<>(new Gson());
    public static synchronized Object parseJson(String json, Class classType){
        return gsonAtomicReference.get().fromJson(json, classType);
    }

    public static String toJson(Object src) {
        return gsonAtomicReference.get().toJson(src);
    }
}
