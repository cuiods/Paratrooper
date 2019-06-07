package src.bean;

public class Box {
	private int point_x;
	private int point_y;
	private boolean isOpen ;
	private int haveSodiler;
	private int value ;   //装备都用钱表示好了emm
	
	/**
	 * 构造方法
	 */
	public Box() {
		this.point_x = 700;
		this.point_y = 600;
		this.isOpen = false;
		this.haveSodiler = 1;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public int getHaveSodiler() {
		return haveSodiler;
	}
	public void setHaveSodiler(int haveSodiler) {
		this.haveSodiler = haveSodiler;
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
