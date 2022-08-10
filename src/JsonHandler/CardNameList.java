package JsonHandler;

import java.util.ArrayList;
import java.util.List;

public class CardNameList {     // Use in CreateScenario to create character from character pool
    private final List<CharacterWithRole> cardNameList = new ArrayList<>();

    public void AddCharacter(CharacterWithRole characterWithRole){
        this.cardNameList.add(characterWithRole);
    }

    public List<CharacterWithRole> GetCardNameList(){
        return cardNameList;
    }

}
