package JsonHandler;

public class CharacterWithRole {    //use in createScenario to create scenario (characters, characters with hidden role,...)
    private final String role;
    private final String name;
    private final String incident;

    public CharacterWithRole(String name, String role,String incident){
        this.name = name;
        this.role = role;
        this.incident=incident;
    }

    public String GetName(){
        return name;
    }

    public String GetRole(){
        return role;
    }

    public String GetIncident(){
        return incident;
    }
}
