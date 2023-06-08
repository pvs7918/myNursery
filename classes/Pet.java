package classes;

public class Pet extends HumanFriends {
    private String favoriteFood;

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }


    @Override
    public String toString() {
        return "Pet [favoriteFood=" + favoriteFood + "]";
    }
}
