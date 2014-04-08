package by.muna.moep.post.cli;

import by.muna.moep.post.IPost;
import by.muna.moep.post.data.IPostType;

public class PostCLI implements IPostCLI {
    private IPost post;

    public PostCLI(IPost post) {
        this.post = post;
    }

    @Override
    public String init() {
        return "Post Price Calculator 0.0.1\nВведите help для помощи";
    }

    @Override
    public String command(String input) throws BadPostCLICommandException {
        try {
            switch (input) {
            case "help":
                return "types -- выводит список типов почтовых отправлений\n" +
                    "type <id> -- выводит параметры типа\n" +
                    "calc <id> <paramName>=<paramValue> <...> -- считает стоимость исходя из введённых параметров";
            case "types": {
                StringBuilder sb = new StringBuilder();

                boolean isFirst = true;
                for (IPostType postType : this.post.getPostTypes()) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append('\n');
                    }

                    sb.append(Integer.toString(postType.getId()));
                    sb.append(" -- ");
                    sb.append(postType.getName());
                }

                return sb.toString();
            }
            default:
                // TODO
                return null;
            }
        } catch (Exception ex) {
            throw new BadPostCLICommandException(ex);
        }
    }
}
