package by.muna.moep.post.data.parameters;

public class PostParameterDataSwitch implements IPostParameterData {
    private IPostParameterSwitch sw;

    public PostParameterDataSwitch(IPostParameterSwitch sw) {
        this.sw = sw;
    }

    public IPostParameterSwitch getSwitch() {
        return this.sw;
    }

    @Override
    public PostParameterType getParameterType() {
        return PostParameterType.SWITCH;
    }
}
