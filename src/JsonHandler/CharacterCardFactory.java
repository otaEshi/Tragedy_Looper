package JsonHandler;

import Cards.CharacterCard;
import JSON.JSONArray;
import JSON.JSONObject;
import JSON.parser.JSONParser;
import JSON.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CharacterCardFactory {
    private ArrayList<CharacterCard> characterCardList;

    public void JSONImportHandler(ArrayList<CharacterCard> characterCardList){ // uses to crate character pool
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<characterCardList.size();i++){
            JSONObject jsonObject = this.ConvertToJSON(characterCardList.get(i));
            jsonArray.add(jsonObject);
        }
        this.WriteToJSONFile(jsonArray,"cardsEN.txt");
    }

    public JSONObject ConvertToJSON(CharacterCard characterCard){
        JSONObject JSONCard = new JSONObject();
        JSONCard.put("cardName",characterCard.GetCardName());
        JSONCard.put("startingPos",Integer.valueOf(characterCard.GetCurrentPos()));
        JSONCard.put("forbidPos",Integer.valueOf(characterCard.GetForbidPos()));
        JSONCard.put("goodwillThreshold1",Integer.valueOf(characterCard.GetGoodwillThreshold1()));
        JSONCard.put("goodwillThreshold2",Integer.valueOf(characterCard.GetGoodwillThreshold2()));
        JSONCard.put("paranoiaThreshold",Integer.valueOf(characterCard.GetParanoiaThreshold()));
        JSONCard.put("imageURL",characterCard.GetImageURL());
        return JSONCard;
    }

    // Write JSON file
    public void WriteToJSONFile(JSONArray jsonArray, String fileName){
        try {
            FileWriter out = new FileWriter(fileName);
            jsonArray.writeJSONString(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // create a list of character cards
    public ArrayList<CharacterCard> CreateFromJSONFile(String fileName){
        try {
            File myObj = new File(fileName);
            Scanner myReader = null;
            myReader = new Scanner(myObj);
            String data = "";
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);
            JSONArray array = (JSONArray)obj;
            characterCardList = new ArrayList<CharacterCard>();
            for (int i=0;i< array.size();i++) {
                JSONObject jsonCard = (JSONObject) array.get(i);                            // create a temp object to init character list
                CharacterCard card = new CharacterCard(jsonCard.get("cardName").toString(), // init charactercard
                        Integer.valueOf(jsonCard.get("startingPos").toString()),
                        Integer.valueOf(jsonCard.get("forbidPos").toString()),
                        Integer.valueOf(jsonCard.get("goodwillThreshold1").toString()),
                        Integer.valueOf(jsonCard.get("goodwillThreshold2").toString()),
                        Integer.valueOf(jsonCard.get("paranoiaThreshold").toString()),
                        jsonCard.get("imageURL").toString());
                characterCardList.add(card);                                                // add cards to list
            }
            return characterCardList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CharacterCard> GetCharacterCardList(){
        return characterCardList;
    }
}