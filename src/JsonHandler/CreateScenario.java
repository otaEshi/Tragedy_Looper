package JsonHandler;
import Cards.CharacterCard;
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

public class CreateScenario {
    private final ArrayList<CharacterCard> characterList;
    private CardNameList cardNameList;
    private final CharacterCardFactory characterCardFactory;

    private final String scenarioName;

    public CreateScenario (String scenarioName,int language){
        this.scenarioName=scenarioName;
        characterList = new ArrayList<>();
        this.CreateFromJSONFile();
        characterCardFactory = new CharacterCardFactory();
        if (language == 1){
            characterCardFactory.CreateFromJSONFile("cardsJP.txt");
        } else if (language == 0){
            characterCardFactory.CreateFromJSONFile("cardsEN.txt");
        }
        // use given scenario to create game with chosen character + hidden role + incident
        for (int i=0;i<cardNameList.GetCardNameList().size();i++){
            for (int j=0;j<characterCardFactory.GetCharacterCardList().size();j++){
                if (cardNameList.GetCardNameList().get(i).GetName().equals(characterCardFactory.GetCharacterCardList().get(j).GetCardName()) ){
                    characterList.add(characterCardFactory.GetCharacterCardList().get(j));
                    characterList.get(j).SetRole(cardNameList.GetCardNameList().get(i).GetRole());
                    characterList.get(j).SetIncident(cardNameList.GetCardNameList().get(i).GetIncident());
                }
            }
        }
    }

    //Write JSON
    public void WriteScenarioToJSONFile(CardNameList cardNameList){ // uses to create scenario pool
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<cardNameList.GetCardNameList().size();i++){
            JSONObject jsonObject = this.ConvertToJSON(cardNameList.GetCardNameList().get(i));
            jsonArray.add(jsonObject);
        }
        this.WriteToJSONFile(jsonArray,scenarioName);
    }

    public JSONObject ConvertToJSON(CharacterWithRole characterWithRole){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CardName",characterWithRole.GetName());
        jsonObject.put("Role",characterWithRole.GetRole());
        jsonObject.put("Incident",characterWithRole.GetIncident());
        return jsonObject;
    }

    public void WriteToJSONFile(JSONArray jsonArray, String fileName){
        try {
            FileWriter out = new FileWriter(fileName);
            jsonArray.writeJSONString(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // create cardNameList
    public CardNameList CreateFromJSONFile(){
        try {
            File myObj = new File(scenarioName);
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
            cardNameList = new CardNameList();
            for (int i=0;i< array.size();i++) {
                JSONObject jsonCard = (JSONObject) array.get(i);
                CharacterWithRole cardName = new CharacterWithRole(jsonCard.get("CardName").toString(),
                                                                   jsonCard.get("Role").toString(),
                                                                   jsonCard.get("Incident").toString());
                cardNameList.AddCharacter(cardName);
            }
            return cardNameList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CharacterCard> GetCharacterList(){
        return characterList;
    }
}
