package by.muna.moep.post.database.tests.factories;

import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.database.PostJSONDatabase;
import by.muna.moep.post.database.JSONParsingException;

public class PostJSONDatabaseFactory {
    public static IPostDatabase createJsonDatabase(String json) throws JSONParsingException {
        return new PostJSONDatabase(json);
    }
}
