package model;

public enum ETypeOfFood {
    DRINK(0, "drink"), BAKERY(1, "bakery");
    private int id;
    private String name;

    ETypeOfFood(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ETypeOfFood getTypeOfFoodById(int id) {
        for (ETypeOfFood eTypeOfFood : values()) {
            if (eTypeOfFood.getId() == id) {
                return eTypeOfFood;
            }
        }
        return null;
    }
    public static ETypeOfFood getTypeOfFoodByName(String name) {
        for (ETypeOfFood eTypeOfFood : values()) {
            if (eTypeOfFood.getName().equals(name)) {
                return eTypeOfFood;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
