package by.muna.moep.post.data.parameters;

public enum PostParameterType {
    SWITCH("switch"), INT("int"), UINT("uint"), FLOAT("float"), UFLOAT("ufloat");

    private String name;

    private PostParameterType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
