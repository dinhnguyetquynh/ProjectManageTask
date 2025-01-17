package model;

public enum Status {

	PENDING("Chưa bắt đầu"), INPROGRESS("Đang làm"), COMPLETED("Hoàn thành");
	
	private String status;
	
	private Status(String status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return status;
    }
}
