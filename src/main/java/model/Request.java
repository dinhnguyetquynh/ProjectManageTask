package model;

import java.io.Serializable;

public class Request<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String message;
    private T data;

    public Request(String message, T data) {
        this.message = message;
        this.data = data;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "Request [message=" + message + ", data=" + data + "]";
	}
}