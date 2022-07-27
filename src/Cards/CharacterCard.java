package Cards;

import javax.swing.*;
import java.awt.*;

public class CharacterCard{
    private final String cardName;
    private int goodwillCount=0;
    private int goodwillThreshold=0;
    private int goodwillThreshold2=9000;
    private int intrigueCount=0;
    private int paranoiaCount=0;
    private int paranoiaThreshold=0;
    private int currentPos;
    private Image image;
    private final String imageURL;
    private Dimension size;
    private final int startingPos;
    private int forbidPos;
    private String role;
    private String incident;
    private boolean isMoving=false;
    private boolean movable=true;
    private boolean horizontalMovable=true;
    private boolean verticalMovable=true;
    private boolean goodwillAddable=true;
    private boolean paranoiaAddable=true;
    private boolean intrigueAddable=true;
    private boolean mastermindCardPlayed=false;
    private boolean protagonistCardPlayed=false;
    private final JLabel characterLabel;
    private boolean forbidMovement=false;
    private int verticalMoveCount=0;
    private int horizontalMoveCount=0;
    private int diagonalMoveCount=0;
    private int addGoodwillCount=0;                 //0-ko add, 1-add 1   2-add 2
    private int addParanoiaCount=0;
    private int addIntrigueCount=0;
    private int alive=1;
    private int tempPos;
    private int thisRoundPos;
    private int thisRoundGoodwill;
    private int thisRoundPara;
    private int thisRoundIntrigue;
    private boolean isAddingGoodwill=false;
    private boolean isAddingPara=false;
    private boolean isAddingIntrigue=false;
    private boolean skill1played=false;
    private boolean skill2played=false;

    public CharacterCard(String cardName, int startingPos,int forbidPos ,int goodwillThreshold1, int paranoiaThreshold, String imageURL) {
        this.cardName = cardName;
        this.startingPos = startingPos;
        this.thisRoundPos = startingPos;
        this.forbidPos = forbidPos;
        this.goodwillThreshold = goodwillThreshold1;
        this.paranoiaThreshold = paranoiaThreshold;
        this.imageURL = imageURL;
        currentPos = startingPos;
        characterLabel = new JLabel();
        movable= forbidPos != 0;
    }

    public CharacterCard(String cardName, int startingPos,int forbidPos,int goodwillThreshold1, int goodwillThreshold2, int paranoiaThreshold, String imageURL) {
        this.cardName=cardName;
        this.startingPos = startingPos;
        this.thisRoundPos = startingPos;
        this.forbidPos = forbidPos;
        this.goodwillThreshold = goodwillThreshold1;
        this.goodwillThreshold2=goodwillThreshold2;
        this.imageURL = imageURL;
        this.paranoiaThreshold = paranoiaThreshold;
        currentPos = startingPos;
        characterLabel = new JLabel();
        movable= forbidPos != 0;
    }

    //1 - hospital / 2 - shrine / 3 - city / 4 - school
    public String GetCardName(){
        return cardName;
    }

    public int GetCurrentPos(){
        return currentPos;
    }

    public void SetCurrentPos(int newPos){
        this.currentPos = newPos;
    }

    public int GetGoodwillCount(){
        return goodwillCount;
    }

    public int GetGoodwillThreshold1(){
        return goodwillThreshold;
    }

    public int GetGoodwillThreshold2(){
        return goodwillThreshold2;
    }

    public int GetIntrigueCount(){
        return intrigueCount;
    }

    public int GetParanoiaThreshold(){
        return paranoiaThreshold;
    }

    public int GetParanoiaCount(){
        return paranoiaCount;
    }

    public void AddGoodwill(int addNum){
        goodwillCount += addNum;
    }

    public void AddIntrigue(int addNum){
        intrigueCount += addNum;
    }

    public void AddParanoia(int addNum){
        paranoiaCount += addNum;
        if (paranoiaCount<0){
            paranoiaCount=0;
        }
    }

    public void SetGoodwillCount(int goodwillCount){
        this.goodwillCount = goodwillCount;
    }

    public void SetParaCount(int paraCount){
        this.paranoiaCount = paraCount;
    }

    public void SetIntrigueCount(int intrigueCount){
        this.intrigueCount = intrigueCount;
    }

    public Image GetImage(){
        return image;
    }

    public String GetImageURL(){
        return imageURL;
    }

    public JLabel GetCharacterLabel(){
        return characterLabel;
    }

    public Dimension GetSize(){
        return size;
    }

    //1-hospital 2-shrine 3-city 4-school 0-all
    public int GetStartingPos(){
        return startingPos;
    }

    public int GetForbidPos(){
        return forbidPos;
    }

    public void SetRole(String role){
        this.role = role;
    }

    public void SetIncident(String incident){
        this.incident = incident;
    }

    public String GetRole(){
        return role;
    }

    public String GetIncident(){
        return incident;
    }

    public boolean GetMovable(){
        return movable;
    }

    public void SetMovable(boolean newState){
        this.movable = newState;
    }

    public boolean GetHorizontalMovable(){
        return horizontalMovable;
    }

    public void SetHorizontalMovable(boolean newState){
        this.horizontalMovable = newState;
    }

    public boolean GetVerticalMovable(){
        return verticalMovable;
    }

    public void SetVerticalMovable(boolean newState){
        this.verticalMovable = newState;
    }

    public boolean GetGoodwillAddable(){
        return goodwillAddable;
    }

    public void SetGoodwillAddable(boolean newState){
        this.goodwillAddable = newState;
    }

    public boolean GetParanoiaAddable(){
        return paranoiaAddable;
    }

    public void SetParanoiaAddable(boolean newState){
        this.paranoiaAddable = newState;
    }

    public boolean GetIntrigueAddable(){
        return intrigueAddable;
    }

    public void SetIntrigueAddable(boolean newState){
        this.intrigueAddable = newState;
    }

    public boolean GetIsMoving(){
        return isMoving;
    }

    public void SetMoving(){
        this.isMoving=true;
    }

    public boolean GetForbidMovement(){
        return forbidMovement;
    }

    public void ResetDay(){
        if (alive==0){
            currentPos=0;
        }
        verticalMoveCount = 0;
        horizontalMoveCount = 0;
        diagonalMoveCount = 0;
        addGoodwillCount = 0;
        addIntrigueCount = 0;
        addParanoiaCount = 0;
        goodwillAddable = true;
        intrigueAddable = true;
        paranoiaAddable = true;
        verticalMovable = true;
        horizontalMovable = true;
        movable = true;
        forbidMovement=false;
        isMoving=false;
        mastermindCardPlayed=false;
        protagonistCardPlayed=false;
        isAddingGoodwill=false;
        isAddingPara=false;
        isAddingIntrigue=false;
        skill2played=false;
        skill1played=false;
    }

    public void ResetLoop(){
        verticalMoveCount = 0;
        horizontalMoveCount = 0;
        diagonalMoveCount = 0;
        addGoodwillCount = 0;
        addIntrigueCount = 0;
        addParanoiaCount = 0;
        paranoiaCount=0;
        goodwillCount=0;
        intrigueCount=0;
        goodwillAddable = true;
        intrigueAddable = true;
        paranoiaAddable = true;
        verticalMovable = true;
        horizontalMovable = true;
        movable = true;
        forbidMovement=false;
        isMoving=false;
        mastermindCardPlayed=false;
        protagonistCardPlayed=false;
        isAddingGoodwill=false;
        isAddingPara=false;
        isAddingIntrigue=false;
        alive=1;
        currentPos=startingPos;
        skill2played=false;
        skill1played=false;
    }

    public int GetAlive(){
        return alive;
    }

    public void SetAlive(int newState){
        this.alive = newState;
    }

    public void AddShield(){
        this.alive+=1;
    }

    public boolean GetMastermindCardPlayed(){
        return mastermindCardPlayed;
    }

    public void SetMastermindCardPlayed(boolean newState){
        this.mastermindCardPlayed = newState;
    }

    public boolean GetProtagonistCardPlayed(){
        return protagonistCardPlayed;
    }

    public void SetProtagonistCardPlayed(boolean newState){
        this.protagonistCardPlayed=newState;
    }

    public int GetTempPos(){
        return tempPos;
    }

    public int GetThisRoundPos(){
        return thisRoundPos;
    }

    public void SetThisRoundPos(int pos){
        this.thisRoundPos=pos;
    }

    public int GetThisRoundGoodwill(){
        return thisRoundGoodwill;
    }

    public int GetThisRoundPara(){
        return thisRoundPara;
    }

    public int GetThisRoundIntrigue(){
        return thisRoundIntrigue;
    }

    public boolean GetIsAddingGoodwill(){
        return isAddingGoodwill;
    }

    public void SetIsAddingGoodwill(boolean newState){
        isAddingGoodwill=newState;
    }

    public boolean GetIsAddingPara(){
        return isAddingPara;
    }

    public void SetIsAddingPara(boolean newState){
        isAddingPara=newState;
    }
    public boolean GetIsAddingIntrigue(){
        return isAddingIntrigue;
    }

    public void SetIsAddingIntrigue(boolean newState){
        isAddingIntrigue=newState;
    }

    public void SurpassForbidPos(){
        forbidPos=9999;
    }

    //police skill can block 1 kill action
    public void KillCharacter(){
        alive-=1;
        if (alive < 0){
            alive=0;
        }
    }

    public boolean GetSkill1Played(){
        return skill1played;
    }

    public void SetSkill1Played(boolean newState){
        this.skill1played=newState;
    }

    public boolean GetSkill2Played(){
        return skill2played;
    }

    public void SetSkill2Played(boolean newState){
        this.skill2played=newState;
    }

    public String GetCurrentPosName(){
        if (currentPos==1){
            return "Hospital";
        }
        if (currentPos==2){
            return "Shrine";
        }
        if (currentPos==3){
            return "City";
        }
        if (currentPos==4) {
            return "School";
        } else return "Archive";
    }
}
