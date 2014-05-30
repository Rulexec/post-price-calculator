package by.muna.moep.post;

import by.muna.moep.post.cli.IPostCLI;
import by.muna.moep.post.cli.PostCLI;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.database.PostJSONDatabase;
import by.muna.moep.post.formula.parser.FormulaParser;
import by.muna.moep.post.formula.parser.IFormulaParser;
import by.muna.moep.post.formula.parser.IFormulaParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception /* TODO: error handling */ {
        File dbFile = Paths.get("db", "db.json").toFile();

        String json = Main.readWholeFile(dbFile);

        IPostDatabase db = new PostJSONDatabase(json);
        IFormulaParserFactory parserFactory = FormulaParser::new;

        IPost post = new Post(db, parserFactory);

        IPostCLI cli = new PostCLI(post);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(cli.init());

        while (true) {
            System.out.print("> ");
            String line = br.readLine();

            String out = cli.command(line);

            System.out.println(out);
        }
    }

    private static String readWholeFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));

        final int size = 16 * 1024;
        char[] buffer = new char[size];

        while (true) {
            int readed = reader.read(buffer);
            sb.append(buffer, 0, readed);

            if (readed < size) break;
        }

        reader.close();

        return sb.toString();
    }
}
