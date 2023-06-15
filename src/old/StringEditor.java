package old;

public class StringEditor {
	
	public static String cutChar(String s, char c) {
		char[] ch = s.toCharArray();
		char[] temp;
		int n = 0;
		for(int i = 0; i < ch.length; i++) {
			if(ch[i] == c) {
				n++;
			}
		}
		temp = new char[ch.length - n];
		for(int i = 0, k = 0; i < ch.length; i++) {
			if(!(ch[i] == c)) {
				temp[i - k] = ch[i];
			} else {
				k++;
			}
		}
		return String.copyValueOf(temp);
	}
	
	public static String replaceChar(String s, char c1, char c2) {
		char[] ch = s.toCharArray();
		char[] temp;
		temp = new char[ch.length];
		for(int i = 0; i < ch.length; i++) {
			if(!(ch[i] == c1)) {
				temp[i] = ch[i];
			} else {
				temp[i] = c2;
			}
		}
		return String.copyValueOf(temp);
	}
	
	public static String pow(String s, int pow) {
		String ret="";
		
		for(int i = 0; i < pow; i++) {
			ret += s;
		}
		
		return ret;
	}
	
	public static String getWord(String s, int num) {
		char[] ch = s.toCharArray();
		char[] temp;
		int i1 = 0, i2 = 0, k = 0;
		for(int n = 0; n < ch.length; n++) {
			if(ch[n] != ' ' && ch[n] != '	') {
				if(n == 0 || (n > 0 && ch[n-1] == ' ' || ch[n-1] == '	')) {
					k++;
					if(k == num) {
						i1 = n;
						break;
					}
				}
			}
		}
		if(k < num)
			return "";
		for(int n = i1; n < ch.length; n++) {
			if(ch[n] == ' ' || ch[n] == '	') {
				i2 = n;
				break;
			}
		}
		if(i2 == 0)
			i2 = ch.length;
		temp = new char[i2-i1];
		for(int n = 0; n < i2-i1; n++) {
			temp[n] = ch[n + i1];
		}
		return String.copyValueOf(temp);
	}
}
