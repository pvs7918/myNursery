package classes;

public abstract class HumanFriends {
    private int id_HF;
    private String Name;        //Имя животного
    private String TypeAnimal;     //Тип животного
    private String CommandsName;     //Названия команд, которым обучен

    public int getId_HF() {
        return id_HF;
    }
    public String getName() {
        return Name;
    }
    public String getTypeAnimal() {
        return TypeAnimal;
    }
    public String getCommandsName() {
        return CommandsName;
    }

    public void setId_HF(int id_HF) {
        this.id_HF = id_HF;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setTypeAnimal(String typeAnimal) {
        TypeAnimal = typeAnimal;
    }
    public void setCommandsName(String commandsName) {
        CommandsName = commandsName;
    }

    public HumanFriends(int id_HF, String name, String typeAnimal, String commandsName) {
        this.id_HF = id_HF;
        Name = name;
        TypeAnimal = typeAnimal;
        CommandsName = commandsName;
    }

    @Override
    public String toString() {
        return "HumanFriends [id_HF=" + id_HF + ", Name=" + Name + ", TypeAnimal=" + TypeAnimal + ", CommandsName="
                + CommandsName + "]";
    }
}

