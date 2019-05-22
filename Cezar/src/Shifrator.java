import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Shifrator {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String textShifr = "", stringText = "";
        byte key;

        try {
            stringText = new String(Files.readAllBytes(Paths.get("src/text.txt")));
            stringText = stringText.toLowerCase();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.print("Введите ключ: ");
        while (!scanner.hasNextByte()) {
            System.out.println("Введите число!");
            scanner.next();
        }
        key = scanner.nextByte();
        char charText[] = stringText.toCharArray();

        for (int i = 0; i < charText.length; i++) {
            if (charText[i] >= 'a' && charText[i] <= 'z') {
                charText[i] = (char) (charText[i] + key);
                if (charText[i] > 'z')
                    charText[i] = (char) (charText[i] % 'z' + 'a' - 1);
                if (charText[i] < 'a')
                    charText[i] = (char) (charText[i] + 26);
            } else
                charText[i] = charText[i];

            textShifr += charText[i];
        }

        System.out.println("Зашифрованный текст: " + textShifr);
        System.out.println("Зашифрованный текст также выведен в файл cezarText.txt");

        try (FileWriter writer = new FileWriter("src/cezarText.txt", false)) {
            writer.write(textShifr);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
