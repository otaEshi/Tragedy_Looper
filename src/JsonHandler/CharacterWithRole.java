package JsonHandler;

public class CharacterWithRole {
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
