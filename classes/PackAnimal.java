package classes;

public class PackAnimal extends HumanFriends {

    private float maxWeight;

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public PackAnimal(int id_HF, String name, String typeAnimal, String commandsName, float maxWeight) {
        super(id_HF, name, typeAnimal, commandsName);
        this.maxWeight = maxWeight;
    }
    
}
