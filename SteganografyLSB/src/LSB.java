import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LSB {

    void addZero(String[][] s, int i, int j) {
        while (1 == 1) {
            if (s[i][j].length() < 8)
                s[i][j] = "0" + s[i][j];
            else
                break;
        }
    }

    void addZero(String[] s, int i) {
        while (1 == 1) {
            if (s[i].length() < 8)
                s[i] = "0" + s[i];
            else
                break;
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static void main(String[] args) throws IOException
    {
        LSB lsb = new LSB();
        BufferedImage image = ImageIO.read(new File("src/input.jpg"));
        Scanner scanner = new Scanner(System.in);

        String[][] stringRed = new String[image.getHeight()][image.getWidth()];
        int[][] intRed = new int[image.getHeight()][image.getWidth()];
        String[][] stringGreen = new String[image.getHeight()][image.getWidth()];
        int[][] intGreen = new int[image.getHeight()][image.getWidth()];
        String[][] stringBlue = new String[image.getHeight()][image.getWidth()];
        int[][] intBlue = new int[image.getHeight()][image.getWidth()];


        String key = "artem";
        String outKey = "";
        int keySize = key.length();
        String allByteKey = "";
        int decimalKey[] = new int[key.length()]; //массив для хранения десятиричного значения ключа
        String[] byteKey = new String[key.length()]; //массив для хранения двоичного значения ключа

        System.out.println("Дано изображение размером " + image.getHeight() + " на " + image.getWidth() + " пикселей");
        System.out.print("Ключ: ");
        for (int i = 0; i < key.length(); i++) {
            decimalKey[i] = Integer.valueOf(key.charAt(i));
            byteKey[i] = Integer.toBinaryString(decimalKey[i]);
            lsb.addZero(byteKey, i);
            System.out.println(byteKey[i]);
        }
        System.out.println("Изначально");
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int clr = image.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                stringRed[i][j] = Integer.toBinaryString(red);
                stringGreen[i][j] = Integer.toBinaryString(green);
                stringBlue[i][j] = Integer.toBinaryString(blue);
                lsb.addZero(stringRed, i, j);
                lsb.addZero(stringGreen, i, j);
                lsb.addZero(stringBlue, i, j);
                System.out.println("Для " + i + "." + j + " = " + stringRed[i][j] + " " + stringGreen[i][j] + " " + stringBlue[i][j]);
            }
        }

        for (int i = 0; i < byteKey.length; i++) //записываем биты всех букв ключа в одну строку
            allByteKey += byteKey[i];

        String[] simbolsByteKey = allByteKey.split(""); //массив содержащий посимвольно все 0 и 1 битов ключа

        //заменяем младшие биты
        int x = 0, y = 0, e=0;
        for (;;) {
            if (e < simbolsByteKey.length) {
                if (y < image.getWidth()) {
                    stringRed[x][y] = removeLastChar(stringRed[x][y]);      //удалили последний элемент строки
                    stringRed[x][y] = stringRed[x][y] + simbolsByteKey[e]; //добавили на его место элемент из ключа
                    e++;                                                   //следующий элемент ключа
                } else {
                    y = 0;
                    x++;
                    stringRed[x][y] = removeLastChar(stringRed[x][y]);      //удалили последний элемент строки
                    stringRed[x][y] = stringRed[x][y] + simbolsByteKey[e]; //добавили на его место элемент из ключа
                    e++;                                                   //следующий элемент ключа
                }
            } else
                break;
            if (e < simbolsByteKey.length) {
                if (y < image.getWidth()) {
                    stringGreen[x][y] = removeLastChar(stringGreen[x][y]);
                    stringGreen[x][y] = stringGreen[x][y] + simbolsByteKey[e];
                    e++;
                } else {
                    y = 0;
                    x++;
                    stringGreen[x][y] = removeLastChar(stringGreen[x][y]);
                    stringGreen[x][y] = stringGreen[x][y] + simbolsByteKey[e];
                    e++;
                }
            } else
                break;

            if (e < simbolsByteKey.length) {
                if (y < image.getWidth()) {
                    stringBlue[x][y] = removeLastChar(stringBlue[x][y]);
                    stringBlue[x][y] = stringBlue[x][y] + simbolsByteKey[e];
                    e++;
                } else {
                    y = 0;
                    x++;
                    stringBlue[x][y] = removeLastChar(stringBlue[x][y]);
                    stringBlue[x][y] = stringBlue[x][y] + simbolsByteKey[e];
                    e++;
                }
            } else
                break;

            y++;
        }

        //переводим стринги в инты
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                intRed[i][j] = Integer.parseInt(stringRed[i][j],2);
                intGreen[i][j] = Integer.parseInt(stringGreen[i][j],2);
                intBlue[i][j] = Integer.parseInt(stringBlue[i][j],2);
            }
        }
        System.out.println("Шифрую сообщение. Подождите немного...");
        File outputfile = new File("src/output.jpg");
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(intRed[i][j], intGreen[i][j], intBlue[i][j]);
                int rgb = color.getRGB();
                image.setRGB(i,j,rgb);
                ImageIO.write(image, "jpg", outputfile);
            }
        }
        System.out.println("Вы очень терпиливы :)");
        System.out.println("Для нового изображения");
        BufferedImage outputFile = ImageIO.read(new File("src/output.jpg"));

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(intRed[i][j], intGreen[i][j], intBlue[i][j]);
                int rgb = color.getRGB();
                outputFile.setRGB(i,j,rgb);
                ImageIO.write(outputFile, "jpg", outputfile);
            }
        }


        String[] result = new String[keySize*8];
        //расшифровка
        //считывает значение пикселя в массивы
        for (int i = 0; i < outputFile.getWidth(); i++) {
            for (int j = 0; j < outputFile.getWidth(); j++) {
                int clr = outputFile.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                stringRed[i][j] = Integer.toBinaryString(red);
                stringGreen[i][j] = Integer.toBinaryString(green);
                stringBlue[i][j] = Integer.toBinaryString(blue);
                lsb.addZero(stringRed, i, j);
                lsb.addZero(stringGreen, i, j);
                lsb.addZero(stringBlue, i, j);
                System.out.println("Для " + i + "." + j + " = " + stringRed[i][j] + " " + stringGreen[i][j] + " " + stringBlue[i][j]);
            }
        }


        int count=0;
        int i =0;
        int j=0;
        //переделать цикл
        for(;;) {
            if (count < result.length) {
                if (j < outputFile.getHeight()) {
                    result[count] = stringRed[i][j].substring(stringRed[i][j].length() - 1);
                    count++;
                } else {
                    j = 0;
                    i++;
                    result[count] = stringRed[i][j].substring(stringRed[i][j].length() - 1);
                    outKey += result[count];
                    count++;
                }
            } else
                break;

            if (count < result.length) {
                if (j < outputFile.getHeight()) {
                    result[count] = stringGreen[i][j].substring(stringGreen[i][j].length() - 1);
                    count++;
                } else {
                    j = 0;
                    i++;
                    result[count] = stringGreen[i][j].substring(stringGreen[i][j].length() - 1);
                    count++;
                }
            } else
                break;

            if (count < result.length) {
                if (j < outputFile.getHeight()) {
                    result[count] = stringBlue[i][j].substring(stringBlue[i][j].length() - 1);
                    count++;
                } else {
                    j = 0;
                    i++;
                    result[count] = stringBlue[i][j].substring(stringBlue[i][j].length() - 1);
                    count++;
                }
            } else
                break;
            j++;
        }
        System.out.println("Расшифрованное сообщение:");
        for(String s : result)
            outKey += s;
        System.out.println(outKey);
        for (String s : outKey.split("(?<=\\G\\d{8})"))
            System.out.print((char) Integer.parseInt(s, 2));}
}

