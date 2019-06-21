package edu.tsinghua.paratrooper.bean;

public class Soldier {
	
	private int id ;
	private String name;
	private int captain;  //军衔
	private String publicKey;  //public key
	private int locationX;
	private int locationY;

	private int groupNum ;  //组号
	private int updateStatus ;
	private int alive;
	private int level;
	private String boxKey;

	public String getBoxKey() {
		return boxKey;
	}

	public void setBoxKey(String boxKey) {
		this.boxKey = boxKey;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCaptain() {
		return captain;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCaptain(int captain) {
		this.captain = captain;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public void setUpdateStatus(int updateStatus) {
		this.updateStatus = updateStatus;
	}

	public void setAlive(int alive) {
		this.alive = alive;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int isCaptain() {
		return captain;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public int getUpdateStatus() {
		return updateStatus;
	}

	public int getAlive() {
		return alive;
	}

	public Soldier() {

    	    this.name = "test";
		    this.locationX = 500;
		    this.locationY = 300;
	}


}
