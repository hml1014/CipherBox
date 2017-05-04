package ciphers;

/* 
 * This class contains methods for encoding 
 * plaintext using different ciphers and
 * decoding encrypted text.
 */

import java.util.LinkedList;

public class Decoder {
	private String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
			                     "n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private String[][] vSquare = new String[26][26];

	/*
	 * This is the constructor method for
	 * the public class Decoder.
	 */
	public Decoder(){
		for (int i=0;i<vSquare.length;i++){
			int t=i;
			for (int j=0;j<vSquare[i].length;j++){
				vSquare[i][j] = alphabet[t];
				t++;
				if (t==26) t=0;
			}
		}
	}
	
	/*
	 * This method accepts a letter as a string and returns
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
	 * This method accepts a letter as a string and returns
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
	 * only digits, whitespace characters, and hyphens. Otherwise
	 * it returns false.
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
	 * all unique letters. Otherwise it returns false.
	 */
	public boolean validateLetters(String s){
		String regEx = "^[A-Za-z]+$";
		if (!s.matches(regEx)) return false;
		if (s.length() > 26) return false;
		
		s = s.toLowerCase();
		String[] c = s.split("(?!^)");
		for(int i=0; i<c.length; i++){
			  for(int j=i + 1; j<c.length; j++){
				  String temp = c[i];
				  String temp2 = c[j];
				  
			     if(temp.equals(temp2)) return false;
			 }
		}
		return true;
	}
	
	/*
	 * This method decodes text that was encoded with the A1Z26 cipher. (See README)
	 */
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
	
	/*
	 * This method encodes text with the A1Z26 cipher. (See README)
	 */
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
	
	/*
	 * This method encodes and decodes text with the Atbash cipher. (See README)
	 */
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
	
	/*
	 * This method encodes and decodes text with the Caesar cipher. (See README)
	 */
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
	
	/*
	 * This method accepts an int n and returns an int a such that
	 * a*n % 26 is equal to 1.
	 */
	public int findInverse(int n){
		for (int i=1;i<26;i++){
			int t = (i*n) %26;
			if (t==1) return i;
		}
		return 0;
	}
	
	/*
	 * This method encodes and decodes text with the Affine cipher. (See README)
	 */
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
	
	/*
	 * This method encodes text with the Railfence cipher. (See README)
	 */
	public String encodeRailfence(String s, int rail){
		String n="";
		String c[] = s.split("(?!^)");
		@SuppressWarnings("unchecked")
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
	
	/*
	 * This method decodes text that was encoded with the Railfence cipher. (See README)
	 */
	public String decodeRailfence(String s, int rail){
		String n="";
		String c[] = s.split("(?!^)");
		@SuppressWarnings("unchecked")
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
	
	/*
	 * This method encodes text with the Vigenere cipher. (See README)
	 */
	public String encodeVigenere(String s, String key){
		String n ="";
		String[] c = s.split("\\s");
		String[] k = key.split("(?!^)");
		int t=0;
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int m = findPlace(c2[j]);
					int m2 = findPlace(k[t]);
					n+= vSquare[m][m2];
					if (t == k.length-1) t=0;
					else t++;
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	/*
	 * This method decodes text that was encoded with the Vigenere cipher. (See README)
	 */
	public String decodeVigenere(String s, String key){
		String n ="";
		String[] c = s.split("\\s");
		String[] k = key.split("(?!^)");
		int t=0;
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int m = findPlace(k[t]);
					int r=0;
					for (int l=0;l<26;l++){
						if (c2[j].equals(vSquare[l][m])) r=l;
					}
					n += alphabet[r];
					if (t == k.length-1) t=0;
					else t++;
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	/*
	 * This method accepts two strings and returns a third
	 * String that combines the first two without any 
	 * whitespace characters if the second string
	 * is shorter than the first.
	 */
	public String makeNewKey(String s, String key){
		String n=key;
		int k = s.length() - key.length();
		if (k > 0){
			String[] c = s.split("\\s");
			for (int i=0;i<c.length;i++){
				String temp =c[i];
				if (!temp.equals("")){
					n += c[i];
				}
			}
		}
		return n;
	}
	
	/*
	 * This method encodes text with the Autokey cipher. (See README)
	 */
	public String encodeAutokey(String s, String key){
		String n ="";
		String newKey = makeNewKey(s,key);
		String[] c = s.split("\\s");
		String[] k = newKey.split("(?!^)");
		int t=0;
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int m = findPlace(c2[j]);
					int m2 = findPlace(k[t]);
					n+= vSquare[m][m2];
					t++;
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	/*
	 * This method decodes text that was encoded with the Autokey cipher. (See README)
	 */
	public String decodeAutokey(String s, String key){
		String n ="";
		LinkedList<String> nk = new LinkedList<String>();
		String[] c = s.split("\\s");
		String[] k = key.split("(?!^)");
		
		for (int i=0;i<k.length;i++){
			nk.add(k[i]);
		}
		
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					int m = findPlace(nk.get(0));
					int r=0;
					for (int l=0;l<26;l++){
						if (c2[j].equals(vSquare[l][m])) r=l;
					}
					nk.add(alphabet[r]);
					nk.remove(0);
					n += alphabet[r];
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	/*
	 * This method accepts a letter and a custom alphabet
	 * as two strings and returns an int representing the 
	 * place of the letter in the alphabet.
	 */
	public int searchKey(String s, String key){
		String[] sub = key.split("(?!^)");
		for (int i=0;i<sub.length;i++){
			if (s.equals(sub[i])) return i;
		}
		return 0;
	}
	
	/*
	 * This method encodes and decodes text using the Simple Substitution cipher. (See README)
	 */
	public String simpleSubstitution(String s, String key, boolean en){
		String n ="";
		String[] c = s.split("\\s");
		String[] sub = key.split("(?!^)");
		for (int i=0;i<c.length;i++){
			String temp =c[i];
			if (!temp.equals("")){
				String[] c2 = temp.split("(?!^)");
				for (int j=0;j<c2.length;j++){
					if (en){
						int t = findPlace(c2[j]);
						n +=sub[t];
					}else{
						int t = searchKey(c2[j], key);
						n +=alphabet[t];
					} 
				}
			}
			
			if (i != c.length-1) n+= " ";
		}
		return n;
	}
	
	/*
	 * This method encodes text using the Columnar Transposition cipher. (See README)
	 */
	public String encodeColumnarTran(String s, String key){
		String n="";
		String c[] = s.split("(?!^)");
		String k[] = key.split("(?!^)");
		int kl = key.length();
		@SuppressWarnings("unchecked")
		LinkedList<String> rf[] = new LinkedList[kl];
		
		for (int i=0;i<kl;i++){
			rf[i] = new LinkedList<String>();
			rf[i].add(k[i]);
		}
		
		int track=0;
		
		for (int i=0;i<c.length;i++){
				rf[track].add(c[i]);
				track++;	
			if (track==kl) track=0;
		}
		
		int rfl = rf.length;
        int r;
        for (int m = rfl; m >= 0; m--) {
            for (int i = 0; i < rfl - 1; i++) {
                r = i + 1;
                int t = findPlace(rf[i].get(0));
                int t2 = findPlace(rf[r].get(0));
                if (t>t2) {
                    LinkedList<String> temp = rf[i];
                    rf[i] = rf[r];
                    rf[r] = temp;
                }
            }
        }
		
		for (int i=0;i<rf.length;i++){
			rf[i].remove(0);
			int lsize = rf[i].size();
			for (int j=0;j<lsize;j++){
				n+= rf[i].get(j);
			}
		}
		
		return n;
	}
	
	/*
	 * This method decodes text that was encoded with the Columnar Transposition cipher. (See README)
	 */
	public String decodeColumnarTran(String s, String key){
		String n="";
		String c[] = s.split("(?!^)");
		String k[] = key.split("(?!^)");
		int kl = key.length();
		@SuppressWarnings("unchecked")
		LinkedList<String> rf[] = new LinkedList[kl];
		
		for (int i=0;i<kl;i++){
			rf[i] = new LinkedList<String>();
			rf[i].add(k[i]);
		}
		
		int track=0;
		
		for (int i=0;i<c.length;i++){
				rf[track].add("*");
				track++;	
			if (track==kl) track=0;
		}
		
		int rfl = rf.length;
        int r;
        for (int m = rfl; m >= 0; m--) {
            for (int i = 0; i < rfl - 1; i++) {
                r = i + 1;
                int t = findPlace(rf[i].get(0));
                int t2 = findPlace(rf[r].get(0));
                if (t>t2) {
                    LinkedList<String> temp = rf[i];
                    rf[i] = rf[r];
                    rf[r] = temp;
                }
            }
        }
        
        int t=0;
        for (int i=0;i<rf.length;i++){
        	int lsize = rf[i].size();
			for (int j=1;j<lsize;j++){
				rf[i].set(j,c[t]);
				t++;
			}
        }
		
        for (int i=0;i<kl;i++){
        	for (int j=0;j<rf.length;j++){
        		String temp = rf[j].get(0);
        		if (temp.equals(k[i])){
        			LinkedList<String> hold = rf[i];
        			rf[i] = rf[j];
        			rf[j]= hold;
        		}
        	}
        }
                
		for (int i=0;i<rf.length;i++){
			rf[i].remove(0);
		}
		
		int track2=0;
		for (int i=0;i<c.length;i++){
				n+= rf[track2].get(0);
				rf[track2].remove(0);
				track2++;
				
				if (track2==kl) track2=0;
				
		}
		
		return n;
	}
}
