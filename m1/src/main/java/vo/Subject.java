package vo;

public class Subject {
	private int subjectNo;
	private String subjectName;
	private int subjectTime;
	private String updatedate;
	public int getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(int subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getSubjectTime() {
		return subjectTime;
	}
	public void setSubjectTime(int subjectTime) {
		this.subjectTime = subjectTime;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	private String createdate;
}
