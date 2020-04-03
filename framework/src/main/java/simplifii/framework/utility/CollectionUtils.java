package simplifii.framework.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by robin on 11/8/16.
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection collection){
        return (collection==null||collection.isEmpty())?true:false;
    }

    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    public static boolean isMapEmpty(Map map){
        return (map==null||map.isEmpty())?true:false;
    }

    public static boolean isMapNotEmpty(Map map){
        return !isMapEmpty(map);
    }

    public static String stringListToString(List<String> stringList){
        if(isNotEmpty(stringList)){
            StringBuilder stringBuilder = new StringBuilder();
            for(String string : stringList){
                stringBuilder.append(string).append(", ");
            }
            if(stringBuilder.length()>1){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            return stringBuilder.toString();
//            return stringList.toString().replace("[","").replace("]","").replaceAll(", ",",");
        }
        return "";
    }
}
