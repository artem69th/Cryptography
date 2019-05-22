package Backpack;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Algoritm {
    static private int[] w = {2, 7, 11, 21, 42, 89, 180, 354}; //супервозрастающая посл-ть
    static public int[] b = new int[8]; //массив для формирования открытого ключа
    static public int sumW, module, factor;
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);
    private String enterText ="";  //вводимы с клавиатуры текст
    private String binaryText = ""; //переменная хранящая бинарный вид каждого символа по очереди
    static private int multiply=0;
    static private int decryNumber=0;

    private int getSumW(int[] w) { //метод нахождения суммы всех значений супервозрастающей послед-ти
        sumW = 0;
        for (int i = 0; i < w.length; i++) {
            sumW += w[i];
        }
        return sumW;
    }

    private int checkMutualSimple(int a, int b) { //алгоритм Евклида для проверки взаимной простоты чисел
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public int generationModule() {
        module = sumW + random.nextInt((2 * sumW) - sumW); //сгенерировали случайное число в пределах sumW до 2sumW
        return module;
    }

    public int generationFactor() { //генерируем множитель (взаимно простой к модулю) в пределах от [1; module)

        factor = random.nextInt(module);
        if (checkMutualSimple(module, factor) == 1)
            return factor;
        else
            generationFactor();
        return 0;
    }

    private void getKey() { //генерация открытого ключа
        for (int i = 0; i < b.length; i++) {
            b[i] = (w[i] * factor) % module;
        }
    }

    private void getEncrypText(int[] decimal, int[] shifrText) { //метод шифрования текста
        for (int i = 0; i < enterText.length(); i++) {
            decimal[i] = Integer.valueOf(enterText.charAt(i)); //перевод теста в десятичный вид
            binaryText = "0" + Integer.toBinaryString(decimal[i]); // из десятичного в двоичный вид. 0 прибавляется потому что при переводе первый ноль откидывается
            if (binaryText.length() != 8)                                //0 прибавляется потому что при переводе первые нули до первой 1 откидываются
                binaryText = "00" + Integer.toBinaryString(decimal[i]);
            char[] binaryChar = binaryText.toCharArray();             //раскладываем бинарное число в массив чар
            for (int y = 0; y < binaryChar.length; y++) {
                if (binaryChar[y] == '0')
                    shifrText[i] += 0 * b[y];
                else if (binaryChar[y] == '1')
                    shifrText[i] += 1 * b[y];
            }
        }

    }

    private void searchMultiplicativ(int module, int factor) { //метод нахождения мультипликативного обратного числа
        for(int i=0; i<module;i++)
        {
            if(i*factor%module==1){
                multiply=i;
                break;
            }
            else
                continue;
        }
    }

    protected void getDecryptionText(int multiply, int[] shifrText, int module, char[] decryText){ //метод расшифровки
        for(int i=0; i<shifrText.length; i++)
        {
            int ascii = 0;
            String binaryDecrText = ""; //строка для записи двоичного представления символа
            decryNumber = shifrText[i] * multiply % module;
            //формируем строку двоичного представления зашифрованного символа
            for(int y=w.length-1; y>=0; y--)
            {
                if(w[y]<=decryNumber){
                    decryNumber = decryNumber - w[y];
                    binaryDecrText += "1";
                }
                else
                    binaryDecrText += "0";
            }
            binaryDecrText = new StringBuffer(binaryDecrText).reverse().toString(); //разворачиваем строку
            ascii = Integer.parseInt(binaryDecrText, 2); //переводим из двоичного в десятичный
            decryText[i] = (char)ascii; //из десятичного в аски и заносим это в массив
        }
    }

    public static void main(String[] args) throws IOException {
        Algoritm algoritm = new Algoritm();
        algoritm.getSumW(w);
        algoritm.generationModule();
        algoritm.generationFactor();
        algoritm.getKey();
        System.out.println("Введите строку, которую Вы хотите зашифровать:");
        algoritm.enterText = scanner.nextLine();
        int[] decimal = new int[algoritm.enterText.length()];
        int[] shifrText = new int[algoritm.enterText.length()];
        char[] decryText = new char[algoritm.enterText.length()];
        algoritm.getEncrypText(decimal,shifrText);
        System.out.println("Зашифрованный текст:");
        for (int i : shifrText)
            System.out.print(i+" ");
        algoritm.searchMultiplicativ(module, factor);
        algoritm.getDecryptionText(multiply, shifrText, module, decryText);
        System.out.println("\nРасшифрованный текст:");
        for(char c : decryText)
            System.out.print(c);
    }
}
//русская буква кодируется дважды: сначала в 11-битный UNICODE, а затем - в 16-битный UTF-8
//Рассмотрим, как кодируется в UTF-8 буква Ж.
// Её UNICODE - 104610 или 041616 или 10000 0101102.
// UNICODE в двоичном виде разбивается на две части: пять левых бит и шесть правых.
// Левая часть дополняется до байта признаком 110 двухбайтного кода UTF-8: 11010000.
// К правой части приписываются два бита 10 признака продолжения многобайтного кода: 10010110.
// Окончательно код буквы Ж в UTF-8 выглядит так:
//        11010000 10010110
