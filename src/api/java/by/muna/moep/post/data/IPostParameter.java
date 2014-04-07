package by.muna.moep.post.data;

import by.muna.moep.post.data.parameters.IPostParameterData;

public interface IPostParameter {
    String getName();
    String getSlug();
    IPostParameterData getData();
}
