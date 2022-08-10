package JsonHandler;

import Cards.PlayerCard;
import JSON.JSONArray;
import JSON.JSONObject;
import JSON.parser.JSONParser;
import JSON.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayerCardFactory {
    private ArrayList<PlayerCard> playerCardList;

    public boolean StringToBoolean(String string) throws Exception {
        if (string.equalsIgnoreCase("true")){
            return true;
        } else if (string.equalsIgnoreCase("false")) {
            return false;
        } else throw new Exception("not true or false");
    }

    public void JSONImportHandler(ArrayList<PlayerCard> playerCardList) {   // uses to create player card pool
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < playerCardList.size(); i++) {
            JSONObject jsonObject = this.ConvertToJSON(playerCardList.get(i));
            jsonArray.add(jsonObject);
        }
        this.WriteToJSONFile(jsonArray, "playerCardEN.txt");
    }

    public JSONObject ConvertToJSON(PlayerCard playerCard) {
        JSONObject JSONCard = new JSONObject();
        JSONCard.put("cardName", playerCard.GetCardName());
        JSONCard.put("imageURL", playerCard.GetImageURL());
        JSONCard.put("reUsable",playerCard.GetReUsable());
        return JSONCard;
    }

    public void WriteToJSONFile(JSONArray jsonArray, String fileName) {
        try {
            FileWriter out = new FileWriter(fileName);
            jsonArray.writeJSONString(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PlayerCard> CreateFromJSONFile(String fileName) {
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
            JSONArray array = (JSONArray) obj;
            playerCardList = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonCard = (JSONObject) array.get(i);

                PlayerCard card = new PlayerCard(jsonCard.get("cardName").toString(), jsonCard.get("imageURL").toString(), StringToBoolean(jsonCard.get("reUsable").toString()));
                playerCardList.add(card);
            }
            return playerCardList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PlayerCard> GetPlayerCardList() {
        return playerCardList;
    }
}