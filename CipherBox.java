package ciphers;

/*
 * This class creates the GUI used for inputting
 * text and keys to be decoded or encoded, along
 * with a selection list of different ciphers
 * to choose from.
 */

import java.awt.*;
import java.awt.event.*;

public class CipherBox extends Frame  {
	private static final long serialVersionUID = 1L;
	private TextArea taInput, taOutput;
	private Button encode, decode;
	private Label lkey, lkey2;
	private TextField key, key2;
	private List list;
	private Decoder d;
	
	/*
	 * This method creates the CipherBox GUI.
	 */
	public CipherBox() {
	    /* Set up layout for the cipher box gui */
	    Panel inputPanel = new Panel(new FlowLayout());
	    Panel keyPanel = new Panel(new FlowLayout());
	    Panel outPanel = new Panel(new FlowLayout());
	    Panel buttonPanel = new Panel(new FlowLayout());
		
	    /* Input */
	    inputPanel.add(new Label("Input   "));
	    taInput = new TextArea("", 5, 50); 
	    taInput.setEditable(true);       
	    inputPanel.add(taInput); 
	    
	    lkey = new Label("Key");
	    key = new TextField("", 10);
	    key.setEditable(true);
	    keyPanel.add(lkey);
	    keyPanel.add(key);
	    
	    lkey2 = new Label("Key 2");
	    key2 = new TextField("", 10);
	    key2.setEditable(true);
	    keyPanel.add(lkey2);
	    keyPanel.add(key2);
	    
	    /* Output */
	    outPanel.add(new Label("Output"));
	    taOutput = new TextArea("", 5, 50); 
	    taOutput.setEditable(false);       
	    outPanel.add(taOutput);
	    
	    /* User options */
	    list = new List(4, false);
	    list.add("A1Z26");
	    list.add("Affine");
	    list.add("Atbash");
	    list.add("Autokey");
	    list.add("Caesar");
	    list.add("Columnar Transposition");
	    list.add("Railfence");
	    list.add("Rot13");
	    list.add("Simple Substitution");
	    list.add("Vigenere");
	    list.select(0);
	    buttonPanel.add(list);
	    
	    encode = new Button("Encode");
	    buttonPanel.add(encode);
	    
	    decode = new Button("Decode");
	    buttonPanel.add(decode);
	    
	    setLayout(new GridLayout(4,1,3,3));  
	    add(inputPanel);
	    add(keyPanel);
	    add(outPanel);
	    add(buttonPanel);
	    
	    setTitle("Cipher Decoder");
	    setSize(500, 450);
	    setVisible(true);
	    
	    /* Add Action Listeners to the box */
	    encode.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	        	d = new Decoder();
	            String input = taInput.getText();
	            String selection = list.getSelectedItem();
	            
	            if (input.matches("^[A-Za-z\\s]+$")){
	            	String output="";
	            	
	            	switch (selection){
	            		case "A1Z26": output = d.encodeA1Z26(input);
	            			break;
	            		case "Affine":
	            			String key01 = key.getText();
	            			String key02 = key2.getText();
	            			if (key01.matches("\\d+") && key02.matches("\\d+")){
	            				int a = Integer.parseInt(key01);
	            				int b = Integer.parseInt(key02);
	            				
	            				if (a%2 ==0 || a<1 || a>25 || a==13){
	            					output = "Invalid key: must be a positive integer between 1 and 25, not coprime to 26.";
	            				}else if (b<1 || b >25) output = "Invalid key 2: must be a positive integer between 1 and 25";
	            				else output = d.affine(input,a,b,true);
	            			}else{
	            				output = "Invalid key(s): both must be a positive integer.";
	            			}
	            			break;
	            		case "Atbash": output = d.atbash(input);
	            			break;
	            		case "Autokey":
	            			String k1 = key.getText();
	            			if (k1.matches("^[a-zA-Z]+$")) output = d.encodeAutokey(input,k1);
	            			else output = "Invalid key: must be letters only.";
	            			break;
	            		case "Caesar": 
	            			String shift = key.getText();
	            			if (shift.matches("\\d+")){
	            				int s = Integer.parseInt(shift);
	            				output = d.caesar(input, s, true);
	            			}else{
	            				output = "Invalid key: must be a nonnegative integer.";
	            			}
	            			break;
	            		case "Columnar Transposition":
	            			String ctkey = key.getText();
	            			boolean ctv = d.validateLetters(ctkey);
	            			if (ctv) output = d.encodeColumnarTran(input,ctkey);
	            			else output = "Invalid key: must contain all unique letters.";
	            			break;
	            		case "Railfence": 
	            			String r = key.getText();
	            			if (r.matches("\\d+")){
	            				int rail = Integer.parseInt(r);
	            				if (rail<1) output = "Invalid key: must be a positive integer.";
	            				else output = d.encodeRailfence(input, rail);
	            			}else output = "Invalid key: must be a positive integer.";
	            			break;
	            		case "Rot13": output = d.caesar(input, 13, true);
	            			break;
	            		case "Simple Substitution":
	            			String sub = key.getText();
	            			if (sub.matches("^[a-zA-Z]+$") && sub.length() == 26){
	            				output = d.simpleSubstitution(input,sub,true);
	            			}else output = "Invalid key: must be 26 letters.";
	            			break;
	            		case "Vigenere": 
	            			String k = key.getText();
	            			if (k.matches("^[a-zA-Z]+$")) output = d.encodeVigenere(input,k);
	            			else output = "Invalid key: must be letters only.";
	            			break;
	            	}
	            	
	            	taOutput.setText(output); 
	            }else {
	            	taOutput.setText("Invalid input: must only contain letters and spaces"); 
	            } 
	         }
	    });
	    
	    decode.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	        	d = new Decoder();
	            String input = taInput.getText();
	            String selection = list.getSelectedItem();
	       
	            if (selection.equals("A1Z26")){
	            	boolean valid = d.validateNums(input);
	            	if (valid==true){
		            	String output = d.decodeA1Z26(input);
		            	taOutput.setText(output); 
		            }else {
		            	taOutput.setText("Invalid input: must only contain numbers 1-26, hyphens, and spaces"); 
		            }
	            }else{
	            	if (input.matches("^[A-Za-z\\s]+$")){
		            	String output="";
		            	
		            	switch (selection){
		            		case "Affine":
		            			String key01 = key.getText();
		            			String key02 = key2.getText();
		            			if (key01.matches("\\d+") && key02.matches("\\d+")){
		            				int a = Integer.parseInt(key01);
		            				int b = Integer.parseInt(key02);
		            				
		            				if (a%2 ==0 || a<1 || a>25 || a==13){
		            					output = "Invalid key: must be a positive integer between 1 and 25, not coprime to 26.";
		            				}else if (b<1 || b >25) output = "Invalid key 2: must be a positive integer between 1 and 25";
		            				else output = d.affine(input,a,b,false);
		            			}else{
		            				output = "Invalid key(s): both must be a positive integer.";
		            			}
		            			break;
		            		case "Atbash": output = d.atbash(input);
	            				break;
		            		case "Autokey":
		            			String k1 = key.getText();
		            			if (k1.matches("^[a-zA-Z]+$")) output = d.decodeAutokey(input,k1);
		            			else output = "Invalid key: must be letters only.";
		            			break;
		            		case "Caesar": 
		            			String shift = key.getText();
		            			if (shift.matches("\\d+")){
		            				int s = Integer.parseInt(shift);
		            				output = d.caesar(input, s, false);
		            			}else{
		            				taOutput.setText("Invalid key, must be a nonnegative integer.");
		            			}
		            			break;
		            		case "Columnar Transposition":
		            			String ctkey = key.getText();
		            			boolean ctv = d.validateLetters(ctkey);
		            			if (ctv) output = d.decodeColumnarTran(input,ctkey);
		            			else output = "Invalid key: must contain all unique letters.";
		            			break;
		            		case "Railfence": 
		            			String r = key.getText();
		            			if (r.matches("\\d+")){
		            				int rail = Integer.parseInt(r);
		            				if (rail<1) output = "Invalid key: must be a positive integer.";
		            				else output = d.decodeRailfence(input, rail);
		            			}else output = "Invalid key: must be a positive integer.";
		            			break;
		            		case "Rot13": output = d.caesar(input, 13, false);
	            				break;
		            		case "Simple Substitution":
		            			String sub = key.getText();
		            			if (sub.matches("^[a-zA-Z]+$") && sub.length() == 26){
		            				output = d.simpleSubstitution(input,sub,false);
		            			}else output = "Invalid key: must be 26 letters.";
		            			break;
		            		case "Vigenere": 
		            			String k = key.getText();
		            			if (k.matches("^[a-zA-Z]+$")) output = d.decodeVigenere(input,k);
		            			else output = "Invalid key: must be letters only.";
		            			break;
		            	}
		            	
		            	taOutput.setText(output); 
		            }else {
		            	taOutput.setText("Invalid input: must only contain letters and spaces"); 
		            } 
	            } 
	         }
	    });
	    
	    addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent evt) {
	            System.exit(0);  
	         }
	    });
	}
}
