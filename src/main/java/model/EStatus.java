package model;

public enum EStatus {
    PAID(1,"paid"),UNPAID(2,"unpaid");
    private int id;
    private String name;
    EStatus(int id, String name) {
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
    public static EStatus getStatusById(int id) {
        for (EStatus eStatus : values()) {
            if (eStatus.getId() == id) {
                return eStatus;
            }
        }
        return null;
    }
    public static EStatus getStatusByName(String name) {
        for (EStatus eStatus : values()) {
            if (eStatus.getName().equals(name)) {
                return eStatus;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
