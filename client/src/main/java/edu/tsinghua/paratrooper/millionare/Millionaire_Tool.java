package edu.tsinghua.paratrooper.millionare;


import edu.tsinghua.paratrooper.millionare.rsa.Tools;
import edu.tsinghua.paratrooper.millionare.rsa.myNumber;

public class Millionaire_Tool {
	private static myNumber X;
	private static int low = 0;
	private static int high = 10;
	private static Tools tools = new Tools();
	private static myNumber P;

	/**
	 * 
	 * @param i 用户A的军衔，军衔的范围是[low, high]
	 * @param Pkb 用户B的公钥
	 * @param N RSA算法中的模数
	 * @return
	 */
	public static String getFirstInfo(int i, String Pkb, String N) {
		Pkb = Pkb.toUpperCase();
		N = N.toUpperCase();
		System.out.println(i + ", " + Pkb + ", " + N);
		X = tools.getPrime(50);
		String code = tools.encrypt(X.toHexadecimal(), new myNumber(Pkb), new myNumber(N));
		code = new myNumber(code).sub(new myNumber(i)).toHexadecimal();
		//System.out.println(X.toHexadecimal() + " " + code);
		return code;
	}
	
	/**
	 * 
	 * @param code 第一步里面A传给B的加密消息
	 * @param j 用户B的军衔
	 * @param Skb 用户B的私钥
	 * @param N RSA算法的模数
	 * @return
	 */
	public static String[] getSecondInfo(String code, int j, String Skb, String N) {
		Skb = Skb.toUpperCase();
		N = N.toUpperCase();
		System.out.println(code + ", " + j + ", " + Skb + ", " + N);
		myNumber[] nums = new myNumber[high - low + 1];
		for(int i = 0; i + low <= high; i++) {
			String codeAddI = new myNumber(code).add(new myNumber(i + low)).toHexadecimal();
			String str = tools.decrypt(codeAddI, new myNumber(Skb), new myNumber(N));
			//System.out.println(str);
			nums[i] = new myNumber(str);
		}
		P = tools.getPrime(40);
		for(int i = 0; i + low <= high; i++) {
			nums[i] = nums[i].mod(P);
		}
		for(int i = j; i + low <= high; i++) {
			myNumber one = new myNumber(1);
			nums[i] = nums[i].add(one);
		}
		String[] nums_str = new String[high - low + 1];
		for(int i = 0; i + low <= high; i++) {
			nums_str[i] = nums[i].toHexadecimal();
		}
		//for(String str : nums_str)
		//	System.out.println(str);
		return nums_str;
	}

	public static  String getP() {
		return P.toHexadecimal();
	}
	
	/**
	 * 
	 * @param nums_str 用户B传给用户A信息
	 * @param i 用户A军衔
	 * @param P 用户B传给用户A的信息
	 * @return true表示自己（调用本方法的人）军衔大，false相反
	 */
	public static boolean  getThirdInfo(String[] nums_str, int i, String P) {
		for(String str : nums_str){
			System.out.print(str + ", ");
		}
		System.out.println(i + ", " + P);
		System.out.println(X);
		String num = new myNumber(X).mod(new myNumber(P)).toHexadecimal();
		return num.compareTo(nums_str[i - low]) != 0;
	}
	
	public static void main(String[] args) {
		Millionaire_Tool a = new Millionaire_Tool();
		int rankA = 2;//a的军衔是5

		Millionaire_Tool b = new Millionaire_Tool();
		int rankB = 5;//b的军衔是3
		String Pkb = "10001";
		String Skb = "A6DD54D8EC5457CB3C3D63EF1C8388C245A58FD8A1B7CA54CEEF31CBD723775CC2A8FE93BB746F00F6FA91D95D451E9AD200607779103A3882FD42EE5423091";
		String N = "CADE36D54FDFF44BE3A9306032B1A82BC260476A9F65CF3895EF88AE6D0D8BF1B4E3849F6445FA8FB37FA40D0CA8BD51A851A4E34716FAB3D93B4EA6FBA14D7";

		//Map<String,String> map_a = RSA_Tool.generateKeys();

		//第一次通信
		String code = a.getFirstInfo(rankA, Pkb, N);   //a要向b通信，用b的公钥加密给b，
		//第二次通信
		String[] nums = b.getSecondInfo(code, rankB, Skb, N);  //b接收该code，用自己的私钥解密，得到nums[] ,将nums[] ,P 传给a
		String P = b.getP();
		//第三次通信
		boolean flag = a.getThirdInfo(nums, rankA, P);    //a用nums，p，自己的rank 得到高or低？
		System.out.println(rankA + " vs " + rankB + " : " + flag);
	}
}
