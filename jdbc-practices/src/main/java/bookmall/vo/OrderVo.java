package bookmall.vo;

public class OrderVo {
	private Long no;
	private String number; // 주문번호
	private int payment; // 결제금액
	private String shipping; // 배송지
	private String status; //주문상태
	
	// user table 속성
	private Long userNo;
	private String userName;
	private String userEmail;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "OrderVo [no=" + no + ", number=" + number + ", payment=" + payment + ", shipping=" + shipping
				+ ", status=" + status + ", userNo=" + userNo + ", userName=" + userName + ", userEmail=" + userEmail
				+ "]";
	}

}
