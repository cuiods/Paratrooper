package Millionare.RAS;

import java.util.LinkedList;
import java.util.Queue;

public class myNumber {
	int[] num;
	static final int BASE = 1 << 28;
	
	public myNumber(String n) {
		int len = (n.length() * 4 - 1) / 28 + 1;
		num = new int[len];
		int it = len - 1;
		char[] chs = n.toCharArray();
		for(int i = n.length() - 1; i > -1; i = i - 7) {
			long cur = 0;
			for(int j = Math.min(6, i); j > -1; j--) {
				char ch = chs[i - j];
				cur = (cur << 4) + toInt(ch);
			}
			num[it--] = (int) cur;
		}
		num = simplify(num);
	}
	
	public myNumber(int n) {
		num = new int[] {n};
		num = simplify(num);
	}
	
	public myNumber(myNumber n) {
		num = n.num;
		num = simplify(num);
	}
	
	public myNumber(int[] n) {
		num = n;
		num = simplify(num);
	}
	
	public myNumber add(myNumber a) {
		int it = num.length - 1, ita = a.num.length - 1;
		int[] res = new int[Math.max(it + 1, ita + 1) + 1];
		int pos = res.length - 1;
		int carry = 0;
		while(it > -1 || ita > -1 || carry > 0) {
			int temp = carry;
			if(it > -1)
				temp += num[it--];
			if(ita > -1)
				temp += a.num[ita--];
			res[pos--] = (temp % BASE);
			carry = temp / BASE;
		}
		res = simplify(res);
		return new myNumber(res);
	}
	
	public myNumber sub(myNumber a) {
		int len = num.length;
		int[] res = new int[len];
		int it = num.length - 1, ita = a.num.length - 1;
		int borrow = 0;
		while(it > -1 || ita > -1) {
			int temp = -borrow;
			temp += num[it];
			if(ita > -1)
				temp -= a.num[ita--];
			borrow = 0;
			if(temp < 0) {
				temp += BASE;
				borrow = 1;
			}
			res[it--] = temp;
		}
		res = simplify(res);
		return new myNumber(res);
	}		

	public myNumber mul(myNumber a) {
		myNumber res = new myNumber(0);
		for(int i = a.num.length - 1, stage = 0; i > -1; i--, stage++) {
			int[] step = new int[num.length + stage + 2];
			long carry = 0;
			int pos = step.length - 1 - stage;
			for(int j = num.length - 1; j > -1; j--) {
				long temp = carry;
				temp += (long)a.num[i] * num[j];
				step[pos--] = (int) (temp % BASE);
				carry = temp / BASE;
			}
			step[pos] = (int) carry;
//			step = simplify(step);
			res = res.add(new myNumber(step));
		}
		res = new myNumber(simplify(res.num));
		return res;
	}
	
	public myNumber div(myNumber a) {
		myNumber res = new myNumber(this);
		myNumber quotient = new myNumber(0);
		while(res.compareTo(a) >= 0) {
			int lendiff = res.getLength() - a.getLength();
			myNumber div = a.leftShift(lendiff - 1);
			myNumber ans = new myNumber("1").leftShift(lendiff - 1);
			myNumber next = div.leftShift(1);
			while(res.compareTo(next) > 0) {
				div = next;
				ans = ans.leftShift(1);
				next = div.leftShift(1);
			}
			res = res.sub(div);
			quotient = quotient.add(ans);
		}
		return quotient;
	}
	
	public myNumber mod(myNumber a) {
		myNumber res = new myNumber(this);
		while(res.compareTo(a) >= 0) {
			int lendiff = res.getLength() - a.getLength();
			myNumber div = a.leftShift(lendiff - 1);
			myNumber next = div.leftShift(1);
			while(res.compareTo(next) > 0) {
				div = next;
				next = div.leftShift(1);
			}
			res = res.sub(div);
			if(res.compareTo(a) == 0)
				return new myNumber(0);
			//
			myNumber ext = div.sub(res);
			if(ext.compareTo(res) < 0 && res.compareTo(a) > 0) {
				lendiff = ext.getLength() - a.getLength();
				div = a.leftShift(lendiff - 1);
				next = div.leftShift(1);
				while(ext.compareTo(next) > 0) {
					div = next;
					next = div.leftShift(1);
				}
				div = next;
				res = div.sub(ext);
			}
		}
		return res;
	}
	
	public myNumber powWithModule(myNumber exp, myNumber n) {
		myNumber ZERO=new myNumber(0);
		myNumber ONE=new myNumber(1);
		if(exp.equals(ZERO))
			return ONE;
		if(exp.equals(ONE))
			return this.mod(n);
		myNumber res = new myNumber("1");
		Queue<Integer> qu = new LinkedList<Integer>();
		for(int it : exp.num) {
			for(int i = 27; i > -1; i--) {
				qu.offer((it >> i) & 1);
			}
		}
		while(qu.peek() == 0)
			qu.poll();
		while(!qu.isEmpty()) {
			if(qu.poll() == 1)
				res = res.mul(this).mod(n);
			if(!qu.isEmpty())
				res = res.mul(res).mod(n);
		}
		return res;
	}
	
	public int compareTo(myNumber a) {
		int len = Math.max(num.length, a.num.length);
		int it = num.length - len, ita = a.num.length - len;
		while(it < num.length) {
			long m = 0, n = 0;
			if(it > -1)
				m = num[it];
			if(ita > -1)
				n = a.num[ita];
			if(m > n)
				return 1;
			if(m < n)
				return -1;
			it++;
			ita++;
		}
		return 0;
	}
	
	public boolean equals(myNumber a) {
		if(num.length != a.num.length)
			return false;
		for(int i = 0; i < num.length; i++)
			if(num[i] != a.num[i])
				return false;
		return true;
	}
	
	public static int[] simplify(int[] res) {
		if(res[0] > 0)
			return res;
		int len = res.length;
		for(int n : res) {
			if(n == 0)
				len--;
			else
				break;
		}
		if(len == 0)
			return new int[] {0};
		int[] simpleRes = new int[len];
		System.arraycopy(res, res.length - len, simpleRes, 0, len);
		return simpleRes;
	}
	
	public myNumber leftShift(int len) {
		if(len <= 0)
			return this;
		int block = len / 28;
		len = len % 28;
		int[] res = new int[num.length + block + 1];
		for(int i = num.length - 1, j = res.length - 1 - block; i > -1; i--, j--) {
			res[j] = num[i];
		}
		if(len == 0)
			return new myNumber(res);
		for(int i = 0; i < res.length; i++) {
			long temp = res[i];
			temp = temp << len;
			if(i < res.length - 1) {
				long temp1 = res[i + 1];
				temp |= (temp1 >> ( 28 - len));
			}
			temp %= (1 << 28);
			res[i] = (int) temp;
		}
		res = simplify(res);
		return new myNumber(res);
	}
	
	public myNumber rightShift(int len) {
		int block = len / 28;
		len = len % 28;
		int[] res = new int[num.length];
		for(int i = res.length - 1; i - block > -1; i--) {
			res[i] = num[i - block];
		}
		for(int i = res.length - 1; i > -1; i--) {
			res[i] = res[i] >> len;
			if(i > 0)
				res[i] |= (res[i - 1] % (1 << len)) << (28 - len);
		}
		res = simplify(res);
		return new myNumber(res);
	}
	
	public String toHexadecimal() {
		StringBuilder sb = new StringBuilder("");
		for(int i = num.length - 1; i > -1; i--) {
			long temp = num[i];
			for(int j = 0; j < 7; j++) {
				sb.insert(0, toChar(temp % 16));
				temp = (temp >> 4);
			}
		}
		while(sb.length() > 1 && sb.charAt(0) == '0')
			sb.delete(0,1);
		return sb.toString();
	}
	
	public int toInt(char ch) {
		if(ch >= 'A')
			return 10 + ch - 'A';
		return ch - '0';
	}
	
	public char toChar(long num) {
		num %= 16;
		if(num >= 10)
			return (char)('A' + num - 10);
		return (char)(num + '0');
	}
	
	public int getLength() {
		long n = num[0];
		int res = 0;
		while(n > 0) {
			res++;
			n = n >> 1;
		}
		return res + (num.length - 1) * 28;
	}
}
