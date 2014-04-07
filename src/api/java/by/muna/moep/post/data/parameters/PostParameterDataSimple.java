package by.muna.moep.post.data.parameters;

public class PostParameterDataSimple implements IPostParameterData {
    private PostParameterType type;

    public PostParameterDataSimple(PostParameterType type) {
        this.type = type;
    }

    @Override
    public PostParameterType getParameterType() {
        return this.type;
    }
}
