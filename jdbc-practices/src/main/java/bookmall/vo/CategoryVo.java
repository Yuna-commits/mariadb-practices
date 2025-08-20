package bookmall.vo;

public class CategoryVo {
	private int no;
	private String type;

	public CategoryVo(String type) {
		super();
		this.type = type;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
