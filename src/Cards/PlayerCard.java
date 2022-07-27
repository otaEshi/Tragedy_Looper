package Cards;

import javax.swing.*;

public class PlayerCard{
    // 1-hospital 2-shrine 3-city 4-school
    private final String cardName;
    private final String imageURL;
    private final JLabel playerCardLabel;
    private boolean playerCardClicked=false;
    private boolean playedState=false;
    private final boolean reUsable;
    private boolean cardUsed=false;

    public PlayerCard(String cardName, String imageURL, boolean reUsable) {
        this.cardName = cardName;
        this.imageURL = imageURL;
        this.reUsable = reUsable;
        playerCardLabel = new JLabel();
    }

    public void HorizontalMovement(CharacterCard characterCard) {
        if (characterCard.GetMovable() && characterCard.GetHorizontalMovable()) {
            if (characterCard.GetCurrentPos() == 1) {
                characterCard.SetCurrentPos(2);
            } else if (characterCard.GetCurrentPos() == 2) {
                characterCard.SetCurrentPos(1);
            } else if (characterCard.GetCurrentPos() == 3) {
                characterCard.SetCurrentPos(4);
            } else if (characterCard.GetCurrentPos() == 4) {
                characterCard.SetCurrentPos(3);
            }
            if (characterCard.GetCurrentPos() == characterCard.GetForbidPos()) {
                characterCard.SetCurrentPos(characterCard.GetThisRoundPos());
            }
            characterCard.SetHorizontalMovable(false);
            characterCard.SetMoving();
        }
    }

    public void VerticalMovement(CharacterCard characterCard) {
        if (characterCard.GetMovable() && characterCard.GetVerticalMovable()) {
            if (characterCard.GetCurrentPos() == 1) {
                characterCard.SetCurrentPos(3);
            } else if (characterCard.GetCurrentPos() == 2) {
                characterCard.SetCurrentPos(4);
            } else if (characterCard.GetCurrentPos() == 3) {
                characterCard.SetCurrentPos(1);
            } else if (characterCard.GetCurrentPos() == 4) {
                characterCard.SetCurrentPos(2);
            }
            if (characterCard.GetCurrentPos() == characterCard.GetForbidPos()) {
                characterCard.SetCurrentPos(characterCard.GetThisRoundPos());
            }
            characterCard.SetVerticalMovable(false);
            characterCard.SetMoving();
        }
    }

    public void DiagonalMovement(CharacterCard characterCard) {
        if (characterCard.GetMovable()) {
            if (characterCard.GetCurrentPos() == 1) {
                characterCard.SetCurrentPos(4);
            } else if (characterCard.GetCurrentPos() == 2) {
                characterCard.SetCurrentPos(3);
            } else if (characterCard.GetCurrentPos() == 3) {
                characterCard.SetCurrentPos(2);
            } else if (characterCard.GetCurrentPos() == 4) {
                characterCard.SetCurrentPos(1);
            }
            if (characterCard.GetCurrentPos() == characterCard.GetForbidPos()) {
                characterCard.SetCurrentPos(characterCard.GetThisRoundPos());
            }
            characterCard.SetMoving();
        }
    }

    //Update method will set all movable to true (in NewGame class)
    public void ForbidMovement(CharacterCard characterCard) {
        characterCard.SetMovable(false);
        if(characterCard.GetIsMoving()){
            characterCard.SetCurrentPos(characterCard.GetThisRoundPos());
        }
    }

    public void AddGoodwill(CharacterCard characterCard, int number) {
        if (characterCard.GetGoodwillAddable()) {
            characterCard.AddGoodwill(number);
            characterCard.SetIsAddingGoodwill(true);
        }
        if (characterCard.GetGoodwillCount()<0){
            characterCard.SetGoodwillCount(0);
        }
    }

    public void ForbidGoodwill(CharacterCard characterCard) {
        characterCard.SetGoodwillAddable(false);
        if (characterCard.GetIsAddingGoodwill()){
            characterCard.SetGoodwillCount(characterCard.GetThisRoundGoodwill());
        }
    }

    public void AddParanoia(CharacterCard characterCard, int number) {
        if (characterCard.GetParanoiaAddable()) {
            characterCard.AddParanoia(number);
            characterCard.SetIsAddingPara(true);
        }
        if (characterCard.GetParanoiaCount()<0){
            characterCard.SetParaCount(0);
        }
    }

    public void ForbidParanoia(CharacterCard characterCard) {
        characterCard.SetParanoiaAddable(false);
        if (characterCard.GetIsAddingPara()){
            characterCard.SetParaCount(characterCard.GetThisRoundPara());
        }
    }

    public void AddIntrigue(CharacterCard characterCard, int number) {
        if (characterCard.GetIntrigueAddable()) {
            characterCard.AddIntrigue(number);
            characterCard.SetIsAddingIntrigue(true);
        }
        if (characterCard.GetIntrigueCount()<0){
            characterCard.SetIntrigueCount(0);
        }
    }

    public void ForbidIntrigue(CharacterCard characterCard) {
        characterCard.SetIntrigueAddable(false);
        if (characterCard.GetIsAddingIntrigue()){
            characterCard.SetIntrigueCount(characterCard.GetThisRoundIntrigue());
        }
    }

    public String GetCardName(){
        return cardName;
    }

    public String GetImageURL(){
        return imageURL;
    }

    public JLabel GetPlayerCardLabel(){
        return playerCardLabel;
    }

    public boolean GetPlayerCardClicked(){
        return playerCardClicked;
    }

    public void SetPlayerCardClicked(boolean newState){
        this.playerCardClicked = newState;
    }

    public void SetPlayedState(boolean newState){
        this.playedState=newState;
    }

    public boolean GetPlayedState(){
        return playedState;
    }

    public boolean GetReUsable(){
        return reUsable;
    }

    public void SetCardUsed(boolean newState){
        cardUsed = newState;
    }

    public boolean GetCardUsed(){
        return cardUsed;
    }

    public void ResetDay(){
        playedState=false;
        playerCardClicked=false;
    }

    public void ResetLoop(){
        playedState=false;
        playerCardClicked=false;
        cardUsed=false;
    }

    public void Reset(){
        cardUsed = false;
    }
}