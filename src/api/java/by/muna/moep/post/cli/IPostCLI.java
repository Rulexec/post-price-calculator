package by.muna.moep.post.cli;

public interface IPostCLI {
    String init();

    String command(String input) throws BadPostCLICommandException;
}
