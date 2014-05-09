package by.muna.moep.post.cli;

import by.muna.moep.post.IPost;
import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;
import by.muna.moep.post.data.parameters.IPostParameterCase;
import by.muna.moep.post.data.parameters.IPostParameterData;
import by.muna.moep.post.data.parameters.PostParameterDataSwitch;
import by.muna.moep.post.data.parameters.PostParameterType;
import by.muna.moep.post.database.models.IDBPostType;

import java.util.HashMap;
import java.util.Map;

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
                if (input.startsWith("type ")) {
                    int id = Integer.parseUnsignedInt(input.substring(5));

                    IPostTypeFull type = this.post.getPostType(id);

                    StringBuilder sb = new StringBuilder();

                    sb.append(type.getName()).append(":\n");

                    boolean isFirst = true;

                    for (IPostParameter parameter : type.getParameters()) {
                        IPostParameterData parameterData = parameter.getData();

                        if (isFirst) isFirst = false;
                        else sb.append('\n');

                        sb.append("  ");

                        sb.append(parameter.getSlug());
                        sb.append(" -- ");
                        sb.append(parameterData.getParameterType().getName());
                        sb.append(" -- ");
                        sb.append(parameter.getName());

                        if (parameterData.getParameterType() == PostParameterType.SWITCH) {
                            sb.append('\n');

                            PostParameterDataSwitch parameterSwitch =
                                (PostParameterDataSwitch) parameterData;

                            boolean isFirstParameter = true;

                            for (IPostParameterCase parameterCase : parameterSwitch.getSwitch().getCases()) {
                                if (isFirstParameter) isFirstParameter = false;
                                else sb.append('\n');

                                sb.append("    ");
                                sb.append(parameterCase.getSlug());
                                sb.append(" -- ");
                                sb.append(parameterCase.getName());
                            }
                        }
                    }

                    return sb.toString();
                } else if (input.startsWith("calc ")) {
                    // s = cli.command("calc 7 int_number=42 num=3.1415 s='planB'");
                    String[] groups = input.split(" ");

                    int id = Integer.parseUnsignedInt(groups[1]);

                    Map<String, String> parameters = new HashMap<>();

                    for (int i = 2; i < groups.length; i++) {
                        String[] ps = groups[i].split("=", 2);
                        parameters.put(ps[0], ps[1]);
                    }

                    long price = this.post.calculate(id, parameters);

                    return Long.toString(price);
                } else {
                    return null;
                }
            }
        } catch (Exception ex) {
            throw new BadPostCLICommandException(ex);
        }
    }
}
