import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deshifrator {
    public static void main(String[] args) throws IOException {
        String cezarText = "";
        char cMax = 0;
        int max = 0, i = 0, count = 0;

        try {
            cezarText = new String(Files.readAllBytes(Paths.get("src/cezarText.txt")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        char c[] = cezarText.toCharArray();
        int buf[] = new int[c.length];
        while (i < c.length) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                buf[c[i] - 'a']++;
                i++;
                count++;
            } else
                i++;
        }

        for (i = 0; i < 26; i++) {
            if (buf[i] > max) {
                max = buf[i];
                cMax = (char) (i + 'a');

            }
            if (buf[i] > 0)
                System.out.println("\t" + (char) (i + 'a') + " --> " + (float) buf[i] / count * 100 + "%");
        }
        System.out.println("Самая большая частота = " + (float) max / count * 100 + "%" + " у буквы " + cMax);
        System.out.println("По статистике самая встречающаяся буква английского алфавита - буква E\nВ файле table.txt приведена таблица частот букв английского алфавита. Если ключ не подошел - попробуйте следущую по частоте букву.");
        byte key = (byte) (cMax - 'e');
        System.out.println("Ключ => " + key);
    }
}
