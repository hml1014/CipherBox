package ciphers;

import java.util.LinkedList;

public class Decoder {
	private String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
			                         "n","o","p","q","r","s","t","u","v","w","x","y","z"};

	public Decoder(){}
	
	/*
	 * This method accepts a letter as string s and returns
	 * an int that is equivalent to the letter, where
	 * a=0, b=1, c=2, ... , z=25
	 */
	public int findPlace(String s){
		int n=0;
		char t = s.charAt(0);
		n = Character.getNumericValue(t)-10;
		return n;
	}
	
	/*
	 * This method accepts a letter as string s and returns
	 * an int that is equivalent to the letter, where
	 * a=1, b=2, c=3, ... , z=26
	 */
	public int ltn(String s){
		int n=0;
		char t = s.charAt(0);
		n = Character.getNumericValue(t)-9;
		return n;
	}
	
	/*
	 * This method returns true if the input string contains
	 * only digits, whitespace characters, and hyphens.
	 */
	public boolean validateNums(String s){
		String regEx = "^[\\d\\s-]+$";
		if (!s.matches(regEx)) return false;
		
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("-");
				for (int j=0;j<c2.length;j++){
					String temp2 = c2[j];
					int t = Integer.parseInt(temp2);
					if (t<1 || t>26) return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * This method returns true if the input string contains
	 * only letters and whitespace characters.
	 */
	public boolean validateLetters(String s){
		String regEx = "^[A-Za-z\\s]+$";
		if (!s.matches(regEx)) return false;
		return true;
	}
	
	public String decodeA1Z26(String s){
		String n ="";
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("-");
				for (int j=0;j<c2.length;j++){
					String temp2 = c2[j];
					int t = Integer.parseInt(temp2);
					t = t-1;
					n += alphabet[t];
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	public String encodeA1Z26(String s){
		String n ="";
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					if (j == c2.length-1) n+= ltn(c2[j]);
					else n+= ltn(c2[j]) + "-";
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	public String atbash(String s){
		String n ="";
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int t = findPlace(c2[j]);
					t = 25-t;
					n += alphabet[t]; 
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	public String caesar(String s, int k, boolean en){
		String n ="";
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int t = findPlace(c2[j]);
					if (en) t = (t+k) % 26;
					else {
						t = t-k;
						if (t<0){
							t=t*-1;
							t = 26 - (t % 26);
						}else{
							t = t% 26;
						}
					}
					n += alphabet[t]; 
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	public int findInverse(int n){
		for (int i=1;i<26;i++){
			int t = (i*n) %26;
			if (t==1) return i;
		}
		return 0;
	}
	
	public String affine(String s, int a, int b, boolean en){
		String n = "";
		String[] c = s.split("\\s");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int t = findPlace(c2[j]);
					if (en) t = (a*t+b) % 26;
					else {
						t = t-b;
						int a1 = findInverse(a);
						if (t<0){
							t=t*a1;
							t=t*-1;
							t = 26 - (t % 26);
						}else{
							t = t*a1 % 26;
						}
					}
					n += alphabet[t]; 
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	public String encodeRailfence(String input, int rail){
		String n="";
		String c[] = input.split("(?!^)");
		LinkedList<String> rf[] = new LinkedList[rail];
		
		for (int i=0;i<rail;i++){
			rf[i] = new LinkedList<String>();
		}
		
		int track=0;
		boolean increase=true;
		
		for (int i=0;i<c.length;i++){
			if (increase){
				rf[track].add(c[i]);
				track++;
			}else{
				rf[track].add(c[i]);
				track--;
			}
			
			if (track==0) increase = true;
			if (track==rail-1) increase = false;
		}
		
		for (int i=0;i<rf.length;i++){
			int lsize = rf[i].size();
			for (int j=0;j<lsize;j++){
				n+= rf[i].get(j);
			}
		}
		
		return n;
	}
	
	public String decodeRailfence(String input, int rail){
		String n="";
		String c[] = input.split("(?!^)");
		LinkedList<String> rf[] = new LinkedList[rail];
		
		for (int i=0;i<rail;i++){
			rf[i] = new LinkedList<String>();
		}
		
		int track=0;
		boolean increase=true;
		
		for (int i=0;i<c.length;i++){
			if (increase){
				rf[track].add("*");
				track++;
			}else{
				rf[track].add("*");
				track--;
			}
			
			if (track==0) increase = true;
			if (track==rail-1) increase = false;
		}
		
		int t=0;
		for (int i=0;i<rf.length;i++){
			int lsize = rf[i].size();
			for (int j=0;j<lsize;j++){
				rf[i].set(j,c[t]);
				t++;
			}
		}
		
		int track2=0;
		boolean increase2=true;
		
		for (int i=0;i<c.length;i++){
			if (increase2){
				n += rf[track2].get(0);
				rf[track2].remove(0);
				track2++;
			}else{
				n += rf[track2].get(0);
				rf[track2].remove(0);
				track2--;
			}
			
			if (track2==0) increase2 = true;
			if (track2==rail-1) increase2 = false;
		}
		
		return n;
	}
}
