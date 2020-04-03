package simplifii.framework.asyncmanager;

/**
 * Created by raghu on 23/8/16.
 */

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import simplifii.framework.exceptionhandler.RestException;

/**
 * Created by nbansal2211 on 09/08/16.
 */
public class FileUploadService extends HttpRestService {

    @Override
    public Object getData(Object... params) throws RestException, JSONException, IOException {
        if (params != null && params.length > 0) {
            FileParamObject param = (FileParamObject) params[0];
            MultipartUtility util = new MultipartUtility(param.getUrl(), "UTF-8");


            for (Map.Entry<String, String> pair : param.getHeaders().entrySet()) {
                util.addHeaderField(pair.getKey(), pair.getValue());
            }
            util.createConnection();
            util.addFilePart(param.getFileKeyName(), param.getFile());
            for (Map.Entry<String, String> pair : param.getPostParams().entrySet()) {
                util.addFormField(pair.getKey(), pair.getValue());
            }
            return parseJson(util.finish(), param);
        } else {
            throw new IllegalArgumentException();
        }
    }
}