package ciphers;

import java.awt.*;
import java.awt.event.*;

public class CipherBox extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TextArea taInput, taOutput;
	private Button encode, decode;
	private Label lkey, lkey2;
	private TextField key, key2;
	private List list;
	private Decoder d;
	
	public CipherBox() {
	    /* Set up layout for the cipher box gui */
	    Panel displayPanel = new Panel(new FlowLayout());
	    Panel outPanel = new Panel(new FlowLayout());
	    Panel buttonPanel = new Panel(new FlowLayout());
	    setLayout(new FlowLayout());
		
	    /* Input */
	    displayPanel.add(new Label("Input"));
	    taInput = new TextArea("", 5, 50); 
	    taInput.setEditable(true);       
	    displayPanel.add(taInput); 
	    
	    lkey = new Label("Key");
	    key = new TextField("", 10);
	    key.setEditable(true);
	    displayPanel.add(lkey);
	    displayPanel.add(key);
	    
	    lkey2 = new Label("Key 2");
	    key2 = new TextField("", 10);
	    key2.setEditable(true);
	    displayPanel.add(lkey2);
	    displayPanel.add(key2);
	    
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
	    list.add("Caesar");
	    list.add("Railfence");
	    list.add("Rot13");
	    list.add("Vigenere");
	    list.select(0);
	    buttonPanel.add(list);
	    
	    encode = new Button("Encode");
	    buttonPanel.add(encode);
	    
	    decode = new Button("Decode");
	    buttonPanel.add(decode);
	    
	    setLayout(new BorderLayout());  
	    add(displayPanel, BorderLayout.NORTH);
	    add(outPanel, BorderLayout.CENTER);
	    add(buttonPanel, BorderLayout.SOUTH);
	    
	    /* Add Action Listeners to the box */
	    encode.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent evt) {
	            d = new Decoder();
	            String input = taInput.getText();
	            boolean valid = d.validateLetters(input);
	            String selection = list.getSelectedItem();
	            
	            if (valid==true){
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
	            		case "Caesar": 
	            			String shift = key.getText();
	            			if (shift.matches("\\d+")){
	            				int s = Integer.parseInt(shift);
	            				output = d.caesar(input, s, true);
	            			}else{
	            				output = "Invalid key: must be a nonnegative integer.";
	            			}
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
	            	boolean valid = d.validateLetters(input);
	            	
	            	if (valid==true){
		            	String output="";
		            	
		            	switch (selection){
		            		case "Atbash": output = d.atbash(input);
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
		            				else output = d.affine(input,a,b,false);
		            			}else{
		            				output = "Invalid key(s): both must be a positive integer.";
		            			}
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
	      
	    setTitle("Cipher Decoder");
	    setSize(750, 400);
	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
	}

}
