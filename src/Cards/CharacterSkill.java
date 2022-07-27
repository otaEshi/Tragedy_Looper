package Cards;

import Ui.NewGame;

import javax.swing.*;

public class CharacterSkill {
    CharacterCard characterCardPerform;
    CharacterCard characterCardTarget;
    NewGame newGame;
    boolean policeSkill1used=false;
    boolean policeSkill2used=false;
    boolean shrineMaidenSkill2Used=false;
    int activatedSkill=0;
    boolean isAdd;

    public void SetIsAdd(boolean isAdd){
        this.isAdd= isAdd;
    }

    public void SetPoliceSkill1Used(boolean newState){
        this.policeSkill1used=newState;
    }

    public void SetPoliceSkill2Used(boolean newState){
        this.policeSkill2used=newState;
    }

    public CharacterSkill(CharacterCard performer, CharacterCard target,NewGame newGame, int activatedSkill){
        this.characterCardPerform = performer;
        this.characterCardTarget = target;
        this.newGame = newGame;
        this.activatedSkill=activatedSkill;
    }

    //Goodwill
    public void BoyStudentSkill (){
        if (characterCardTarget.GetParanoiaAddable() && characterCardTarget.GetCardName().equals("Girl Student")) {
            characterCardTarget.AddParanoia(-1);
        }
    }

    public void GirlStudentSkill (){
        if (characterCardTarget.GetParanoiaAddable() && characterCardTarget.GetCardName().equals("Boy Student")) {
            characterCardTarget.AddParanoia(-1);
        }
    }

    // Remove or add 1 para
    public void DoctorSkill1(boolean isAdd){
        if (characterCardTarget.GetParanoiaAddable()) {
            if (isAdd) {
                characterCardTarget.AddParanoia(1);
            } else characterCardTarget.AddParanoia(-1);
        }
    }

    public void DoctorSkill2(){
        if (characterCardTarget.GetCardName().equals("Patient")){
            characterCardTarget.SurpassForbidPos();
        }
    }

    //create pop-up show role
    public String OfficeWorkerSkill(){
        return characterCardPerform.GetRole();
    }

    //reveal role
    public String ShrineMaidenSkill2(){
        shrineMaidenSkill2Used=newGame.GetShrineMaidenSkill2Used();
        if (shrineMaidenSkill2Used==false){
            return characterCardTarget.GetRole();
        }  else return "";
    }

    //Limit once per loop
    public void PoliceSkill1(){
        if (policeSkill1used==false) {
            if (newGame.GetIsMurderHappen()) {
                for (int i=0;i<newGame.GetScenario().GetCharacterList().size();i++) {
                    if (newGame.GetScenario().GetCharacterList().get(i).GetIncident().equals("Murder")) {
                        newGame.GetIncidentInfor1().setText("Murder incident's culprit is: " +newGame.GetScenario().GetCharacterList().get(i).GetCardName());
                        newGame.GetIncidentInfor1().setVisible(true);
                        break;
                    }
                }
            }
            if (newGame.GetIsSuicideHappen()) {
                for (int i=0;i<newGame.GetScenario().GetCharacterList().size();i++) {
                    if (newGame.GetScenario().GetCharacterList().get(i).GetIncident().equals("Suicide")) {
                        newGame.GetIncidentInfor2().setText("Suicide incident's culprit is: " +newGame.GetScenario().GetCharacterList().get(i).GetCardName());
                        newGame.GetIncidentInfor2().setVisible(true);
                        break;
                    }
                }
            }
            newGame.GetIncidentInfor1().setText("No incident occurred");
            newGame.GetIncidentInfor1().setVisible(true);
            policeSkill1used=true;
            newGame.GetHiddenRolePanel().setVisible(true);
        }
    }

    public void PoliceSkill2(){
        if(policeSkill2used==false){
            if (characterCardTarget.GetAlive()!=0){
                characterCardTarget.AddShield();
                System.out.println("check police skill 2 working??");
            }
            policeSkill2used=true;
        }
    }

    public void ActiveGoodwillSkill(){
        if (characterCardPerform.GetCardName().equals("Doctor")){
            if (newGame.GetCurrentSkill()==1){
                DoctorSkill1(isAdd);
                characterCardPerform.SetSkill1Played(true);
            } else if (newGame.GetCurrentSkill()==2){
                DoctorSkill2();
                characterCardPerform.SetSkill2Played(true);
            }
        } else if (characterCardPerform.GetCardName().equals("Boy Student")){
            if (!characterCardTarget.GetCardName().equals("Boy Student") && characterCardTarget.GetCurrentPos()==characterCardPerform.GetCurrentPos()) {
                BoyStudentSkill();
                characterCardPerform.SetSkill1Played(true);
            }
        } else if (characterCardPerform.GetCardName().equals("Girl Student")) {
            if (!characterCardTarget.GetCardName().equals("Girl Student") && characterCardTarget.GetCurrentPos()==characterCardPerform.GetCurrentPos()) {
                GirlStudentSkill();
                characterCardPerform.SetSkill1Played(true);
            }
        } else if (characterCardPerform.GetCardName().equals("Office Worker")){
            OfficeWorkerSkill();
            characterCardPerform.SetSkill1Played(true);
        } else if (characterCardPerform.GetCardName().equals("Police")){
            if (newGame.GetCurrentSkill()==1){
                this.SetPoliceSkill1Used(newGame.GetPoliceSkill1Used());
                if (policeSkill1used==false) {
                    PoliceSkill1();
                    newGame.SetPoliceSkill1Used(true);
                    newGame.SetPoliceSkill1Used(true);
                    System.out.println("check 2");
                }
            } else if (newGame.GetCurrentSkill()==2){
                this.SetPoliceSkill2Used(newGame.GetPoliceSkill2Used());
                if (policeSkill1used==false) {
                    PoliceSkill2();
                    newGame.SetPoliceSkill2Used(true);
                }
            }
        } else if (characterCardPerform.GetCardName().equals("Shrine Maiden")){
            if (newGame.GetCurrentSkill()==2){
                if (shrineMaidenSkill2Used==false){
                    ShrineMaidenSkill2();
                }
            }
        }
    }

    // Hidden role
    //optional
    public void BrainSKill(CharacterCard characterCardTarget){
        if (characterCardTarget.GetIntrigueAddable()!=false) {
            characterCardTarget.AddIntrigue(1);
        }
    }

    //optional
    public void ConspiracyTheoristSkill(CharacterCard characterCardTarget){
        if (characterCardTarget.GetParanoiaAddable()!=false) {
            characterCardTarget.AddParanoia(1);
        }
    }

    //mandatory
    public void SerialKillerSkill(){
        characterCardTarget.KillCharacter();
    }

    //mandatory
    public void KillerSkill(){
        for (int i=0;i<newGame.GetScenario().GetCharacterList().size();i++){
            if (newGame.GetScenario().GetCharacterList().get(i).GetRole().equals("Key Person")){
                characterCardTarget=newGame.GetScenario().GetCharacterList().get(i);
            }
            if (newGame.GetScenario().GetCharacterList().get(i).GetRole().equals("Killer")){
                characterCardPerform=newGame.GetScenario().GetCharacterList().get(i);
            }
        }
        if (characterCardTarget.GetIntrigueCount()>=2 && characterCardPerform.GetCurrentPos()==characterCardTarget.GetCurrentPos() ){
            characterCardTarget.KillCharacter();
        }
        if (characterCardPerform.GetIntrigueCount()>=4){
            JOptionPane.showMessageDialog(newGame.GetMenu(), "Protagonist died, loop end");
            for (int i=0;i<newGame.GetScenario().GetCharacterList().size();i++){
                if (newGame.GetScenario().GetCharacterList().get(i).GetRole().equals("Key Person")){
                    newGame.GetScenario().GetCharacterList().get(i).SetAlive(0);
                    break;
                }
            }
        }
    }

}
