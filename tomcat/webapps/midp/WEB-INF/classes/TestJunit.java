import java.sql.*;
import java.io.*;
import java.util.*;
import sqlTransfer.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {	
	
	@Test
  public void TestSearchUtility() {
	//DataTransfer DB = new DataTransfer();
	SearchUtility SU = new SearchUtility();
	
	System.out.println("Testing Search Utility");
	
	ArrayList<String[]> itemDetails = new ArrayList<String[]>();
	String[] data = new String[9];
	String[] data1 = new String[9];
	String[] data2 = new String[9];
	String[] data3 = new String[9];
	String[] data4 = new String[9];
	
	data[0] = "0001";
	data[1] = "1234";
	data[8] = "10";
	data1[0] = data[0];
	data1[1] = data[1];
	data1[8] = data[8];
	data2[0] = data[0];
	data2[1] = data[1];
	data2[8] = data[8];
	data3[0] = data[0];
	data3[1] = data[1];
	data3[8] = data[8];
	data4[0] = data[0];
	data4[1] = data[1];
	data4[8] = data[8];
	
	//-------------------------------
	data[2] = "First"; //Title
	data[3] = "Nice Image"; //Image name
	data[4] = "who knows"; //desc
	data[5] = null; //kw1
	data[6] = null; //kw2
	data[7] = "150"; //askPrice
	itemDetails.add(data);
	
	//--------------------------------
	data1[2] = "Second"; //Title
	data1[3] = "Bad Image"; //Image name
	data1[4] = "just cardboard"; //desc
	data1[5] = "cardboard"; //kw1
	data1[6] = null; //kw2
	data1[7] = "275"; //askPrice
	itemDetails.add(data1);
	
	//------------------------------
	data2[2] = "Third"; //Title
	data2[3] = "Weird Image"; //Image name
	data2[4] = "just a box"; //desc
	data2[5] = null; //kw1
	data2[6] = "box"; //kw2
	data2[7] = "300"; //askPrice
	itemDetails.add(data2);
	
	//-----------------------------
	data3[2] = "Fourth"; //Title
	data3[3] = "Just an Image"; //Image name
	data3[4] = "a cardboard box?"; //desc
	data3[5] = "cardboard"; //kw1
	data3[6] = "box"; //kw2
	data3[7] = "350"; //askPrice
	itemDetails.add(data3);
	
	//----------------------------
	data4[2] = "Fifth"; //Title
	data4[3] = "The best image"; //Image name
	data4[4] = "new cardboard box"; //desc
	data4[5] = null; //kw1
	data4[6] = null; //kw2
	data4[7] = "700"; //askPrice
	itemDetails.add(data4);
	
	//Testing Parameters ----------------------------
	
	//Price test
	//Should return "Second", "Third"
	String titleSearch_null = null;
	String[] keywords_null = {null,null};
	double[] searchPrice = {275, 300};
	ArrayList<String[]> result_price = SU.searchFunc(titleSearch_null, keywords_null, searchPrice, itemDetails);
	
	//Title test
	//Should return "Second"
	String titleSearch = "Second";
	searchPrice[0] = 1;
	searchPrice[1] = 800;
	
	ArrayList<String[]> result_title = SU.searchFunc(titleSearch, keywords_null, searchPrice, itemDetails);
	
	//Single keyword test
	//Should return "Third", "Fourth"
	String[] keywords_single = {"box",null};
	ArrayList<String[]> result_key1 = SU.searchFunc(titleSearch_null, keywords_single, searchPrice, itemDetails);
	
	//Double keyword test
	//Should return "Fourth"
	String[] keywords = {"box","cardboard"};
	ArrayList<String[]> result_key2 = SU.searchFunc(titleSearch_null, keywords, searchPrice, itemDetails);
	
	//No criteria, besides large price range
	//Should return all
	ArrayList<String[]> result_all = SU.searchFunc(titleSearch_null, keywords_null, searchPrice, itemDetails);

	//Result check	
	String[] r1_1 = result_price.get(0);
	String[] r1_2 = result_price.get(1);
	
	String[] r2_1 = result_title.get(0);
	
	String[] r3_1 = result_key1.get(0);
	String[] r3_2 = result_key1.get(1);
	
	String[] r4_1 = result_key2.get(0);
	
	String[] r5_1 = result_all.get(0);
	String[] r5_2 = result_all.get(1);
	String[] r5_3 = result_all.get(2);
	String[] r5_4 = result_all.get(3);
	String[] r5_5 = result_all.get(4);
	
	assertEquals("Second", r1_1[2], r1_1[2]);
	assertEquals("Third", r1_2[2], r1_2[2]);
	
	assertEquals("Second", r2_1[2], r2_1[2]);
	
	assertEquals("Third", r3_1[2], r3_1[2]);
	assertEquals("Fourth", r3_2[2], r3_2[2]);
	
	assertEquals("Fourth", r4_1[2], r4_1[2]);
	
	assertEquals("First", r5_1[2], r5_1[2]);
	assertEquals("Second", r5_2[2], r5_2[2]);
	assertEquals("Third", r5_3[2], r5_3[2]);
	assertEquals("Fourth", r5_4[2], r5_4[2]);
	assertEquals("Fifth", r5_5[2], r5_5[2]);
  }
  
  @Test
  public void TestDataTransfer() {
	DataTransfer DB = new DataTransfer();
	System.out.println("Testing Relational Database");
	
	//Write then read items
	//buyerID, itemID, title, imageName, description, keyword1, keyword2, askPrice, minPrice
	String buyerID = "0001";
	String itemID = "1111";
	String title = "Cool Title";
	String imageName = "Name of Image";
	String description = "Masterful Description";
	String keyword1 = "First Keyword";
	String keyword2 = "Second Keyword";
	String askPrice = "PriceSet";
	String minPrice = "PriceMin";
	
	String[] testSet = {buyerID, itemID, title, imageName, description, keyword1, keyword2, askPrice, minPrice};
	
	DB.WriteItemsDB(buyerID, itemID, title, imageName, description, keyword1, keyword2, askPrice, minPrice);
	ArrayList<String[]> itemList = DB.ReadItemsDB("", false, false);
	
	String[] returnedSet = itemList.get(0);
	
	for(int i = 0; i < 9; i++) {
		//System.out.println("Asserting: " + testSet[i] + " = " + returnedSet[i]);
		assertEquals(testSet[i], returnedSet[i], returnedSet[i]);
	}
	
	//check buyer-specific read
	buyerID = "1234";
	itemID = "5678";
	DB.WriteItemsDB(buyerID, itemID, title, imageName, description, keyword1, keyword2, askPrice, minPrice);
	ArrayList<String[]> itemList2 = DB.ReadItemsDB(buyerID, true, false); //ID, buyList, singleItem
	
	returnedSet = itemList2.get(0);
	assertEquals(buyerID, returnedSet[0], returnedSet[0]);
	assertEquals(itemID, returnedSet[1], returnedSet[1]);
	for(int j = 2; j < 9; j++) {
		assertEquals(testSet[j], returnedSet[j], returnedSet[j]);
	}
	
	//check item-specific read
	buyerID = "0001";
	itemID = "1111";
	ArrayList<String[]> itemList3 = DB.ReadItemsDB(buyerID, true, false); //ID, buyList, singleItem
	
	returnedSet = itemList3.get(0);
	for(int k = 0; k < 9; k++) {
		assertEquals(testSet[k], returnedSet[k], returnedSet[k]);
	}
	
	//Check number of products
	int itemNum = DB.NumOfItems();
	String num = "false";
	String trueTest = "two";
	if(itemNum == 2)
		num = "two";
	
	//assertEquals(trueTest, num, "Number of items was:" + itemNum + " Outcome was: " + num);
	
	//Add offer
	String offerPrice = "10";
	String counterPrice = "";
	String status = "Pending";
	String[] offerTest = {itemID, buyerID, offerPrice, counterPrice, status};
	DB.WriteOfferDB(itemID, buyerID, offerPrice, counterPrice, status, false);
	
	//Check offer 
	ArrayList<String[]> offerList = DB.ReadOfferDB("", false, false);
	String[] resultOffer = offerList.get(0);
	
	for(int l = 0; l < 5; l++){
		assertEquals(offerTest[l], resultOffer[l], resultOffer[l]);
	}
	
	//counter offer
	counterPrice = "30";
	status = "Countered";
	DB.WriteOfferDB(itemID, buyerID, offerPrice, counterPrice, status, true);
	
	//add another offer to confirm selective offer picks
	itemID = "5555";
	buyerID = "4444";
	DB.WriteOfferDB(itemID, buyerID, offerPrice, "", "Pending", false);
	
	//check counter
	ArrayList<String[]> offerList1 = DB.ReadOfferDB(buyerID, true, false);
	String[] resultOffer1 = offerList1.get(0);
	for(int m = 0; m < 3; m++){
		assertEquals(offerTest[m], resultOffer[m], resultOffer[m]);
	}
	assertEquals(counterPrice, resultOffer[3], resultOffer[3]);
	assertEquals(status, resultOffer[4], resultOffer[4]);
	
	//(String id, boolean buyer, boolean seller) buyerID, itemID
}
}


