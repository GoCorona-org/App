package simplifii.framework.Network;

public class NetworkResponse<T> {
	private int responseCode;
	private boolean isSuccess;
	private boolean isTimeout;
	private String reason;
	private boolean isConnectedToNetwork = true;
	private boolean isURLValid = true;
	private T data;
	private String urlLInk;

	public String getUrlLInk() {
		return urlLInk;
	}

	public void setUrlLInk(String urlLInk) {
		this.urlLInk = urlLInk;
	}

	public boolean isConnectedToNetwork() {
		return isConnectedToNetwork;
	}

	public void setConnectedToNetwork(boolean isConnectedToNetwork) {
		this.isConnectedToNetwork = isConnectedToNetwork;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public NetworkResponse<T> setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		return this;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isTimeout() {
		return isTimeout;
	}

	public void setTimeout(boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	public boolean isURLValid() {
		return isURLValid;
	}

	public void setURLValid(boolean isURLValid) {
		this.isURLValid = isURLValid;
	}

}
