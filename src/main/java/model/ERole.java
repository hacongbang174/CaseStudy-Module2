package model;

public enum ERole {
    admin(0,"admin"),
    customer(1,"customer");
    private int id;
    private String name;

    ERole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ERole getRoleById(int id) {
        for (ERole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
    }
    public static ERole getRoleByName(String name) {
        for (ERole role : values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }
}
