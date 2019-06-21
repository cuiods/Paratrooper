package edu.tsinghua.paratrooper.bean;

public class Box {
	private int id;
	private int point_x;
	private int point_y;
	private int apply ;   //已经有多少人参与开箱
	private int total;    //总共需要多少人
	private int status ;   //是否开箱

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 构造方法
	 */
	public Box() {
		this.point_x = 700;
		this.point_y = 600;
		this.apply = 0;

		this.total = 5;
		this.status = 0;
	}


	public int getApply() {
		return apply;
	}

	public void setApply(int apply) {
		this.apply = apply;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	

}
