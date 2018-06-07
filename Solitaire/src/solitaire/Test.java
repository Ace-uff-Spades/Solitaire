package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Character;
/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Test {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	CardNode rearKey;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	   
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
		//makeCopy();
	}
	
	public CardNode previous(CardNode target)
	{
		CardNode itr = new CardNode();
		itr = target.next; 
		while(itr.next!=target){
			itr = itr.next;
		}
		return itr;  
	}
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 * Switches the 27 over one place 
	 */
	void jokerA() {
		
		CardNode itr = new CardNode(); 
		itr = deckRear;
		if(itr.cardValue == 27)
		{
			CardNode temp = itr.next;
			previous(itr).next = temp;
			itr.next = temp.next; 
			temp.next = itr;
			deckRear = temp; 
			//System.out.println("JokerA is done--->");
			return;
		}
		
		while(itr.cardValue!=27){
			itr = itr.next;
		}
		
		if(itr.cardValue==27 && itr.next!=deckRear){
			CardNode temp = itr.next;
			previous(itr).next = temp;
			itr.next = itr.next.next; 
			temp.next = itr;
			//System.out.println("JokerA is done--->");
			return;
		}
		else if(itr.cardValue==27 && itr.next==deckRear){
			CardNode temp = itr.next;
			previous(itr).next = temp;
			itr.next = temp.next; 
			temp.next = itr;
			deckRear = itr;
			//System.out.println("JokerA is done--->");
			return;
		}
					
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 * Switches the 28 over two nodes
	 */
	void jokerB() {
	    
		CardNode itr= deckRear;
		if(itr.cardValue == 28){
			CardNode temp = itr.next;
			previous(itr).next = itr.next;
			itr.next = itr.next.next.next;
			temp.next.next=itr;
			deckRear = temp;
		//	System.out.println("Joker B is done-->");
			return;
		}
		
		while(itr.cardValue != 28){
			itr = itr.next; 
		}
		
		if(itr.cardValue == 28 && itr.next==deckRear){
			CardNode temp = itr.next;
			previous(itr).next = itr.next;
			itr.next = itr.next.next.next;
			temp.next.next=itr;
			deckRear = previous(itr);
			//System.out.println("Joker B is done-->");
			return; 
			
		}
		if(itr.cardValue == 28 && itr.next.next==deckRear){
			CardNode temp = itr.next;
			previous(itr).next = itr.next;
			itr.next = itr.next.next.next;
			temp.next.next=itr;
			deckRear = itr;
			//System.out.println("Joker B is done-->");
			return; 
		}
		else {
			CardNode temp = itr.next;
			previous(itr).next = itr.next;
			itr.next = itr.next.next.next;
			temp.next.next=itr;
			//System.out.println("Joker B is done-->");
		}	
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 * Cuts the deck to three parts. The middle part being the numbers in between 27 and 28. 
	 * Then it switches the ends
	 */
	void tripleCut() {
		if(deckRear.cardValue == 27 || deckRear.cardValue == 28){
			CardNode target = deckRear; 
			while(target.next.cardValue!=27 && target.next.cardValue!=28){
				target = target.next;
			}
			deckRear=target;
		}
		else if(deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28){
			CardNode target = deckRear.next; 
			while(target.next.cardValue!=27 && target.next.cardValue!=28){
				target = target.next;
			}
			deckRear=target.next;
		}
		else {
			CardNode firstB = new CardNode();
			CardNode firstE = new CardNode();
			CardNode secondB = new CardNode();
			CardNode secondE = new CardNode();
			CardNode coreB = new CardNode();
			CardNode coreE = new CardNode();
			
			firstB = deckRear.next;
			CardNode itr = deckRear.next;
			while(itr.cardValue != 27 && itr.cardValue != 28){
				itr = itr.next;
			}
			firstE = previous(itr);
			coreB = itr; 
			itr = itr.next;
			
			while(itr.cardValue != 27 && itr.cardValue != 28){
				itr = itr.next;
			}
	
			coreE = itr; 
			secondB = itr.next;
			secondE = deckRear;
			
			
			coreE.next=firstB;
			firstE.next = secondB;
			secondE.next=coreB;
			deckRear = firstE;
		}
		
		//System.out.println("TripleCut is done---->");
	}
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 * takes the number at the end of the deck. counts that many from the start and puts 
	 * it right before the last node
	 */
	void countCut() {	
		CardNode itr = deckRear;
		CardNode frontF = deckRear.next;
		if(itr.cardValue == 27 || itr.cardValue == 28){
			
		}
		else{
			for(int i = 0; i<deckRear.cardValue; i++)
				itr=itr.next;
			CardNode frontE = itr;
			CardNode secondF = itr.next; 
			CardNode secondE = previous(deckRear); 
			
			frontE.next = deckRear; 
			deckRear.next = secondF; 
			secondE.next = frontF; 
		}
		
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey()
	{	
	
		int depth = 0;
		
		boolean flag = false; 
		int Counter = 0;
		while(flag != true){
			//Counter++;
			//System.out.println(Counter);
			jokerA();
			//System.out.println("JokerA -- >");
			//printList(deckRear);
			jokerB();
			//System.out.println("JokerB -- >");
			//printList(deckRear);
			tripleCut();
			//System.out.println("tripleCut-- >");
			//printList(deckRear);
			countCut();
			//System.out.println("countCut -- >");
			//printList(deckRear);
			
			CardNode itr = deckRear.next;
			if(itr.cardValue == 28)
				depth = 27;
			else
				depth = itr.cardValue;
			
			for(int i =0; i<depth-1; i++){
				itr = itr.next;
			}
			if(itr.next.cardValue!= 27 && itr.next.cardValue != 28){
				CardNode key = itr.next;
				flag = true;
				return key.cardValue;
			}
		}
	    return -1;
	}
	
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			
			ptr = ptr.next;
			//if(ptr.cardValue==27 || ptr.cardValue==28)
				//System.out.print("," + ptr.cardValue);
			//else
			System.out.print("," + ptr.cardValue);
			
		} while (ptr != rear);
		
		
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) 
	{	

		//LOADING A LINKED LIST WITH MESSAGE'S INTEGER VALUES
		message.toUpperCase();
		CardNode rear = new CardNode();
		rear.cardValue=message.charAt(message.length()-1)-64;
		CardNode temp = rear;
		for(int i =0; i<message.length(); i++){
			Character one = message.charAt(i);
			if(one.isLetter(one)){
				CardNode add = new CardNode();
				add.cardValue = message.charAt(i)-64;
				temp.next = add; 
				temp = add;
			}
		}
		temp.next = rear; 
		
		
		// GET THE KEYSTREAM
		rearKey = new CardNode();
		rearKey.cardValue = getKey();
		rearKey.next = rearKey; 
		CardNode itrR = rear.next;
		while(itrR != rear){
			int key = getKey(); 
			CardNode add = new CardNode(); 
			add.cardValue=key; 
			add.next=rearKey.next;
			rearKey.next = add;
			rearKey = add;
			itrR = itrR.next;
		}
		
		//ENCRYPTING MESSAGE
		CardNode itrM = rear.next;      //Message iterator
		CardNode itrK = rearKey.next;   //Key iterator
		CardNode rearE = new CardNode();//Encryption iterator
		rearE.cardValue = itrM.cardValue + itrK.cardValue;
		if(rearE.cardValue>26)
			rearE.cardValue = rear.cardValue-26;
		rearE.next = rearE; 
		itrM = itrM.next;
		itrK = itrK.next;
		while(itrM != rear.next && itrK != rearKey.next){
			int plus = itrM.cardValue + itrK.cardValue; 
			if(plus > 26)
				plus = plus-26;
			CardNode add = new CardNode();
			add.cardValue=plus;
			add.next=rearE.next;
			rearE.next = add; 
			rearE = add;
			itrM = itrM.next;
			itrK = itrK.next;
		}
		
		
		//DISPLAY ENCRYPTION
		//System.out.println();
		//System.out.println("Encyrption");
		CardNode itrE= rearE.next; 
		String Emessage = "";
		while(itrE != rearE){
			Emessage = Emessage + (char)(itrE.cardValue+64);
			itrE = itrE.next;
		}
		
		
	    return Emessage;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {
		
		//LOAD UP A LINKED LIST WITH ENCRYPTED MESSAGE  
		message.toUpperCase();
		CardNode rearE = new CardNode();
		rearE.cardValue=message.charAt(message.length()-1)-64;
		CardNode temp = rearE;
		for(int i =0; i<message.length(); i++){
			Character one = message.charAt(i);
			if(one.isLetter(one)){
				CardNode add = new CardNode();
				add.cardValue = message.charAt(i)-64;
				temp.next = add; 
				temp = add;
			}
		}
		temp.next = rearE;
		
		
		// GET THE KEYSTREAM
		rearKey = new CardNode();
		rearKey.cardValue = getKey();
		rearKey.next = rearKey; 
		CardNode itrR = rearE.next;
		while(itrR != rearE){
			int key = getKey(); 
			CardNode add = new CardNode(); 
			add.cardValue=key;
			add.next=rearKey.next;
			rearKey.next = add;
			rearKey = add;
			itrR = itrR.next;
		}
	
			
		//DECRYPTING THE MESSAGE 
		CardNode itrE = rearE.next;      //Message iterator
		CardNode itrK = rearKey.next;    //Key iterator
		CardNode rearD = new CardNode(); //Deryption iterator
		if(rearE.cardValue<= itrK.cardValue)
			rearE.cardValue = rearE.cardValue + 26; 
		rearD.cardValue = itrE.cardValue - itrK.cardValue;
		rearD.next = rearD; 
		itrE = itrE.next;
		itrK = itrK.next;
		while(itrE != rearE.next && itrK != rearKey.next){
			if(itrE.cardValue <= itrK.cardValue){
				itrE.cardValue = itrE.cardValue + 26;
			}
			int diff = itrE.cardValue - itrK.cardValue;
			CardNode add = new CardNode();
			add.cardValue=diff; 
			add.next=rearD.next;
			rearD.next = add;
			rearD = add; 
			itrE = itrE.next;
			itrK = itrK.next;
		}
		
		//DISPLAY 
		//System.out.println();
		//System.out.println("Decryption");
		CardNode itrDecrypt = rearD.next;
		String Decryption = "";
		while(itrDecrypt != rearD){
			Decryption = Decryption + (char)(itrDecrypt.cardValue+64);
			itrDecrypt = itrDecrypt.next;
		}

	    return Decryption;
	}
	
}
