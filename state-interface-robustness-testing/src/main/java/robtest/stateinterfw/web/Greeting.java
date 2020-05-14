package robtest.stateinterfw.web;

public class Greeting {
   private String name;
   private String label;

    public Greeting(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }
}
