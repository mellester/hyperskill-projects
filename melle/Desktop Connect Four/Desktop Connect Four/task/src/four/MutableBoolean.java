package four;

public class MutableBoolean {
    public MutableBoolean(boolean b) {
        this.value = b;
    }

    public boolean booleanValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        value = !value;
    }

    private boolean value = false;

}
