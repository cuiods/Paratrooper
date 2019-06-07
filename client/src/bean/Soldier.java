package src.bean;

public class Soldier {
	
	private int id ;
	private String name;
	private int level;
	private boolean isLeader;
	private String pub_key;  //public key
	private String pri_key ; //private key
	private int code  ;  //携带的密码段
	private int point_x;
	private int point_y;
	private int group ;
	

	private static String[] map = {"士兵","中卫","上校"};
	
    public Soldier() {
    	    this.name = "test";
		this.point_x = 500;
		this.point_y = 300;
		this.isLeader = false;
	}
    
    public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getPoint_x() {
		return point_x;
	}
	public void setPoint_x(int point_x) {
		this.point_x = point_x;
	}
	public int getPoint_y() {
		return point_y;
	}
	public void setPoint_y(int point_y) {
		this.point_y = point_y;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isLeader() {
		return this.isLeader;
	}
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
	public String getPub_key() {
		return pub_key;
	}
	public void setPub_key(String pub_key) {
		this.pub_key = pub_key;
	}
	public String getPri_key() {
		return pri_key;
	}
	public void setPri_key(String pri_key) {
		this.pri_key = pri_key;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String levelToString() {
		return map[level];
	}

}
