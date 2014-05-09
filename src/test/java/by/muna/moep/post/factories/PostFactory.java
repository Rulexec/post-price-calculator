package by.muna.moep.post.factories;

import by.muna.moep.post.IPost;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.formula.parser.IFormulaParser;

public class PostFactory {
    public static IPost createPost(IPostDatabase db, IFormulaParser formulaParser) {
        throw new RuntimeException("Post not implemented yet.");
    }
}
