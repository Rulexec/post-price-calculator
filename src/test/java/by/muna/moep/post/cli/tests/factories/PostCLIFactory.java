package by.muna.moep.post.cli.tests.factories;

import by.muna.moep.post.IPost;
import by.muna.moep.post.cli.IPostCLI;
import by.muna.moep.post.cli.PostCLI;

public class PostCLIFactory {
    public static IPostCLI createPostCLI(IPost post) {
        return new PostCLI(post);
    }
}
