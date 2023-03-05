package geektime.spring.springbucks.model;

public enum OrderState {
    INIT("INIT", 1),
    PAID("PAID", 2),
    BREWING("BREWING", 3),
    BREWED("BREWED", 4),
    TAKEN("TAKEN", 5),
    CANCELLED("CANCELLED", 6);

    private String key;
    private int state;

    OrderState(String name, int state) {
        this.key = name;
        this.state = state;
    }

    public String getKey() {
        return key;
    }

    public int getState() {
        return state;
    }

}
