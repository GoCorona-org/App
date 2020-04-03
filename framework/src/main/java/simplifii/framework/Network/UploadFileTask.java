package simplifii.framework.Network;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Code got From and modified as per our need:
 * http://stackoverflow.com/questions/5415390/sending-pictures-to-a-web-server
 * 
 * 
 */
public class UploadFileTask extends ClientURLConnection {

	private static final String boundary = "*****";
	private static final String lineEnd = "\r\n";
	private static final String twoHyphens = "--";
	private static final String separator = twoHyphens + boundary + lineEnd;
	private String filePath;
	private Map<String, String> paramaters1;

	public UploadFileTask(String URL, Map<String, String> parameters,
			String filePath) {
		super(URL);
		this.method = POST_METHOD;
		this.filePath = filePath;
		this.paramaters1 = parameters;
	}
	public UploadFileTask(String URL, String filePath){
		super(URL);
		this.method = POST_METHOD;
		this.filePath = filePath;
		init();
		
	}
	
	
	
	
	

	@Override
	public void sendDataInPostMethod(DataOutputStream dos, String paramToSend)
			throws IOException {
		// send text key value pairs first
	//	sendTextParams(dos);
		File file = new File(filePath);

		int maxBufferSize = 64 * 1024; // 64 kilobyes
		

        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"attachFile\";filename=\""+ filePath + "\"" + lineEnd);
        dos.writeBytes(lineEnd);


		// Read file and create buffer
		FileInputStream fileInputStream = new FileInputStream(file);
		int bytesAvailable = fileInputStream.available();
		int bufferSize = Math.min(bytesAvailable, maxBufferSize);
		byte[] buffer = new byte[bufferSize];
		// Send file data
		int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		while (bytesRead > 0) {
			// Write buffer to socket
			dos.write(buffer, 0, bufferSize);
			

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		}

		// send multipart form data necesssary after file data
		dos.writeBytes(lineEnd);
		dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		dos.flush();
		dos.close();
		fileInputStream.close();
	}

	@Override
	protected void init() {
		super.init();

		contentType = "multipart/form-data;boundary=" + boundary;
	}

	private void sendTextParams(DataOutputStream dos) throws IOException {
		Set<String> allKeys = paramaters1.keySet();
		for (String key : allKeys) {
			writeFormField(dos, separator, key, paramaters1.get(key));
		}
	}

	private void writeFormField(DataOutputStream dos, String separator,
			String fieldName, String fieldValue) throws IOException {
		dos.writeBytes(separator);
		dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName
				+ "\"\r\n");
		dos.writeBytes("\r\n");
		dos.writeBytes(fieldValue);
		dos.writeBytes("\r\n");
	}
	
	/*private void showData(HttpURLConnection conn){
		Logger.debug("SHOW DATA METHOD", "CALLED");
		try {
			InputStream is = conn.getInputStream();
			if(is != null)
			Logger.debug("SHOW DATA METHOD", "CALLED is not NULL");
			BufferedReader buReader = new BufferedReader(
					new InputStreamReader(is), 50000);
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = buReader.readLine()) != null) {
				response.append(line);
			}

		Logger.debug("DATA", response.toString());
		
		} catch (IOException e) {
			
			
		} finally {
			
		}
	}*/
}
