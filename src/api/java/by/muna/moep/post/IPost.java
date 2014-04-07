package by.muna.moep.post;

import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;

import java.util.List;
import java.util.Map;

public interface IPost {
    List<IPostType> getPostTypes() throws PostException;
    IPostTypeFull getPostType(int id) throws PostException;

    long calculate(int postTypeId, Map<String, String> arguments) throws PostException;
}
