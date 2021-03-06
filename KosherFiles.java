package KosherFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


class KosherFiles {
    public static void main(String[] args) throws NumberFormatException {
        System.out.println("Shalom!\n");
        System.out.println("I will now check whether you have files created on Shabbat.");
        System.out.println("To continue press any button, to leave enter -1");
        Scanner reader = new Scanner(System.in);
        String input = reader.next();


        boolean wishesToContinue;

        try {
            int choice = Integer.parseInt(input);
            if (choice == -1) {
                wishesToContinue = false;
            } else {
                wishesToContinue = true;
            }
        } catch (NumberFormatException e) {
            wishesToContinue = true;
        }

        if (!wishesToContinue) {
            System.out.println("I see...\nGoodbye");
            System.exit(0);
        } else {
            try {
                Path startdir = Paths.get(System.getProperty("user.home"));
                //Path startdir = Paths.get("/home/achaad/Downloads");
                ChangeMetadata kosher = new ChangeMetadata();
                Files.walkFileTree(startdir, kosher);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

