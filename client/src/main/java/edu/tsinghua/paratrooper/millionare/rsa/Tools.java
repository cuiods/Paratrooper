package edu.tsinghua.paratrooper.millionare.rsa;

import java.util.Random;

public class Tools {
	
	public myNumber getPrime(int len) {
		int count = 0;
		if(len < 256) {
			count = 8;
		}
		else if(len < 384) {
			count = 3;
		}
		else if(len < 512) {
			count = 2;
		}
		else 
			count = 2;
		myNumber num = getRandom(len);
		while(true) {
			if(primeTest1(num) && primeTest2(num, count, 32))
				break;
			num = getRandom(len);
		}
		return num;
	}
	
	public myNumber getRandom(int len) {
		Random rand = new Random();
		int block = (len - 1) / 28 + 1;
		int[] num = new int[len];
		int it = len - 1;
		for(int i = 0; i < block; i++) {
			int cur = 0;
			if(i < block - 1) {
				cur = rand.nextInt((1 << 28));
				if(i == 0 && (cur & 1) == 0) {
					cur--;
				}
			}
			else {
				for(int j = (len % 28 - 1) / 4 + 1; j > 0; j--) {
					int temp = rand.nextInt(16);
					cur = (cur << 4) + temp;
				}
			}
			num[it--] = cur;
		}
		num = myNumber.simplify(num);
		myNumber res = new myNumber(num);
		return res;
	}
	
	public boolean primeTest1(myNumber num) {
		myNumber ZERO=new myNumber(0);
		int[] tests = new int[] {3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
		for(int test : tests) {
			if(num.mod(new myNumber(test)).compareTo(ZERO) == 0)
				return false;
		}
		return true;
	}
	
	public boolean primeTest2(myNumber num, int k, int len) {
		myNumber ONE=new myNumber(1);
		myNumber d = num.sub(ONE);
		myNumber numSubOne = new myNumber(d);
		int r = 0;
		while((d.num[d.num.length - 1] & 1) == 0) {
			d = d.rightShift(1);
			r++;
		}
		while(k-- > 1) {
			myNumber a = new myNumber(new Random().nextInt(1 << 28));
			myNumber x = a.powWithModule(d, num);
			if(x.equals(ONE) || x.equals(numSubOne))
				continue;
			int ct = r;
			while(ct-- > 0 && !x.equals(ONE) && !x.equals(numSubOne)) {
				x = x.mul(x).mod(num);
			}
			if(ct < r && !x.equals(numSubOne))
				return false;
		}
		return true;
	}

	public myNumber getPhi(myNumber a) {
		myNumber res = new myNumber(1);
		myNumber base = new myNumber(2);
		myNumber num = new myNumber(a);
		myNumber zero = new myNumber(0);
		myNumber one = new myNumber(1);
		while(num.compareTo(one) != 0) {
			if(num.mod(base).compareTo(zero) == 0) {
				num = num.div(base);
				myNumber temp = new myNumber(1);
				while(num.mod(base).compareTo(zero) == 0) {
					num = num.div(base);
					temp = temp.mul(base);
				}
				res = res.mul(temp.mul(base.sub(one)));
			}
			base = base.add(one);
		}
		return res;
	}
	
	public myNumber getPhi(myNumber a, myNumber b) {
		myNumber one = new myNumber(1);
		return a.sub(one).mul(b.sub(one));
	}
	
	public myNumber getE(myNumber n) {
		return new myNumber(3);
	}
	
	public myNumber getE(myNumber n, int len) {
		myNumber one = new myNumber(1);
		myNumber res = new myNumber("10001");
		while(extgcd(res, n)[2].compareTo(one) != 0)
			res = res.add(one);
		return res;
	}
	
	public myNumber getD(myNumber e, myNumber phi_n) {
		myNumber d = inverse(e, phi_n);
		return d;
	}
	
	public String encrypt(String plane, myNumber e, myNumber n) {
		int block = 100;
		char[] chs = plane.toCharArray();
		StringBuilder res = new StringBuilder();
		for(int i = 0; i < plane.length(); i++) {
			int ch = chs[i];
			res.append(toHex(ch / 16));
			res.append(toHex(ch));
		}
		String str = res.toString();
		StringBuilder temp = new StringBuilder("");
		while(str.length() > block) {
			temp.append(new myNumber(str.substring(0, block)).powWithModule(e, n).toHexadecimal());
			temp.append("#");
			str = str.substring(block);
		}
		temp.append(new myNumber(str).powWithModule(e, n).toHexadecimal());
		return temp.toString();
	}
	
	public char toHex(long num) {
		num %= 16;
		if(num >= 10)
			return (char)('A' + num - 10);
		return (char)(num + '0');
	}
	
	public int toBin(String str) {
		int res = 0;
		for(char ch : str.toCharArray()) {
			res = res << 4;
			if(ch >= 'A')
				res +=  10 + ch - 'A';
			else
				res += ch - '0';
		}
		return res;
	}
	
	public String decrypt(String code, myNumber d, myNumber n) {
		StringBuilder sb = new StringBuilder();
		String[] strs = code.split("#");
		StringBuilder str = new StringBuilder("");
		for(String it : strs) {
			String part = new myNumber(it).powWithModule(d, n).toHexadecimal();
			str.append(part);
		}
		//System.out.println(code + " " + str);
		for(int j = 0; j + 2 < str.length() + 1; j += 2) {
			char ch = (char)(toBin(str.substring(j, j + 2)));
			String s = "0123456789ABCDEF";
			if(s.contains(String.valueOf(ch)))
				sb.append(ch);
			else {
				int r = toBin(str.substring(j, j + 2)) % 16;
				ch = s.charAt(r);
				sb.append(ch);
			}
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public myNumber[] extgcd(myNumber x,myNumber y){
		myNumber res[]=null;
		myNumber zero = new myNumber(0);
		myNumber one = new myNumber(1);
		if(y.equals(zero)){
			res = new myNumber[4];
			res[0] = one;
			res[1] = zero;
			res[2] = x;
			res[3] = y;
		}else{
			res = extgcd(y, x.mod(y));
			res[1] = res[0].add(x.div(y).mul(res[0] = res[1]));
			res[3] = one.sub(res[3]);
		}
		return res;
	}
	
	public myNumber inverse(myNumber e, myNumber phi_n){
		myNumber res[] = extgcd(e, phi_n);
		myNumber one = new myNumber(1);
		return res[3].compareTo(one) == 0 ? phi_n.sub(res[0]) : res[0];
	}
	/*
	public static void main(String[] args) {
		Tools tools = new Tools();
		int count = 100;
		long time = System.nanoTime();
		for(int i = 0; i < count; i++) {
			tools.getPrime(256);
		}
		System.out.println((System.nanoTime() - time) / 1000000 / count);
		time = System.nanoTime();
		for(int i = 0; i < count; i++) {
			tools.getPrime(384);
		}
		System.out.println((System.nanoTime() - time) / 1000000 / count);
	}*/
}
