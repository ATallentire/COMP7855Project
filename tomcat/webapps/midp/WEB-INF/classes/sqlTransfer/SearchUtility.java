package sqlTransfer;
import java.text.ParseException;
import java.io.*;
import java.util.*;


public class SearchUtility {

	//This function takes the search criteria and all photo data and finds matches between the two
	//Must Pass searchDist and searchLoc as doubles, everything else is a string

    public static ArrayList<String[]> searchFunc(String titleSearch, String[] keywords, double[] searchPrice, ArrayList<String[]> itemDetails) {
		System.out.println("Entered Search Func");
		
        ArrayList<String[]> itemGallery = new ArrayList<String[]>();
		int keyCheck = 0;
		try{
		//keyCheck: 0 = no keywords, 1 = keyword[0], 2 = keyword[1], 3 = both
		if(keywords[0] == null) {
			if(keywords[1] != null)
				keyCheck = 2;
		}
		else {
			if(keywords[1] != null)
				keyCheck = 3;
			else
				keyCheck = 1;
		}
		}
		catch(Exception ex) {
			System.out.println("Failed: " + ex);}
			
		System.out.println("Passed KeyCheck");
		
        for (int i = 0; i < itemDetails.size(); i++) {

			//data: 0 image name. 1 caption, 2 date, 3 lat, 4 lon
			String[] data = new String[9];
            data = itemDetails.get(i);
/*
					data[0] = rs.getString("buyerID");

					data[1] = rs.getString("itemID");

					data[2] = rs.getString("imageName");

					data[3] = rs.getString("description");

					data[4] = rs.getString("keyword1");

					data[5] = rs.getString("keyword2");

					data[6] = rs.getString("askPrice");

					data[7] = rs.getString("minPrice");
*/
			String desc = data[4];
			String title = data[2];
			String kw1 = data[5];
			String kw2 = data[6];
			String askPrice = data[7];
			double price = 0;
			
			try{
				price = Double.parseDouble(askPrice);
			}
			catch (Exception e) {}

			//searchPrice[0] = min, 1 = max
            if (price >= searchPrice[0] && price <= searchPrice[1]) {
				
                if (keyword_match(keywords, kw1, kw2, keyCheck)) {
					if(titleSearch == null)
						itemGallery.add(data);
					
                    else if(title.matches("(.*)" + titleSearch + "(.*)"))
						itemGallery.add(data);
				}
             
            }
        }

        return itemGallery;
    }

    public static boolean keyword_match(String[] keywords, String kw1, String kw2, int keyID){
		//Check if either of the search keywords are null, and/or if either of the item keywords are null
		//Pass if they are both null, if the one not null matches, or if both matches when neither are null
		//fail if there is a failed match with a non-null value
		if(kw1 == null)
			kw1 = "";
		if(kw2 == null)
			kw2 = "";
		
		switch(keyID)
		{
			case 0:
				return true;
			case 1:
				if(kw1.matches(keywords[0]) || kw2.matches(keywords[0]))
					return true;
				else
					return false;
			case 2:
				if(kw1.matches(keywords[1]) || kw2.matches(keywords[1]))
					return true;
				else
					return false;
			case 3:
				if((kw1.matches(keywords[0]) || kw2.matches(keywords[0])) && (kw1.matches(keywords[1]) || kw2.matches(keywords[1])))
					return true;
				else
					return false;
			default:
			return false;
		}
    }
}
