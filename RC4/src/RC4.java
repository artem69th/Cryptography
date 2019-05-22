import java.io.Console;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class RC4 {
    private int[] S = new int[256]; //массив инициализации
    private int i,j; //счетчики

    private void swap(int[] arr, int i, int j){ //метод перестановки

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void init(byte[] key){ //метод инициализаяя массива S
        for (i = 0; i < 256; i++)
            S[i] = i;
        j = 0;
        for (i = 0; i < 256; i++)
        {
            j = (j + S[i] + key[i % key.length]) % 256;
            swap(S, i, j);
        }
        i=0;
        j=0;
    }

    byte grw(){ //генератор случайного слова K
        i=(i+1)%256;
        j=(j+S[i])%256;
        swap(S, i ,j);
        byte K = (byte) S[(S[i] + S[j])%256]; //K - псевдослучайное слово
        return K;
    }

    byte[] CryDecry(byte[] a, byte[] b){ //метод шифрования и разшифрования

        for(int i = 0; i<a.length; i++)
            b[i] = (byte) (a[i]^grw());

        for(byte bt : b)
            System.out.print(bt);
        return b;
    }


    public static void main(String[] args) {
        byte[] byteText, byteKey, CryText, DecryText; //массивы для хранения байтовых представлений теста, ключа, за/расшифрованного текста
        String str; //строка для вывода расшифрованного текста
        Scanner scanner = new Scanner(System.in);

        //входные данные
        System.out.print("Введите текст: ");
        String text = scanner.nextLine();
        byteText = text.getBytes();
        System.out.print("Ваш текст в байтах: ");
        for (byte b : byteText)
            System.out.print(b);
        System.out.print("\nВведите ключ: ");
        String keyStr = scanner.nextLine();
        byteKey = keyStr.getBytes();

        //шифрование
        RC4 rc4 = new RC4();
        CryText = new byte[byteText.length];
        System.out.print("Зашифрованный текст: ");
        rc4.init(byteKey);
        rc4.CryDecry(byteText, CryText);

        //дешифрование
        rc4 = new RC4();
        System.out.print("\nРасшифрованный текст в байтах: ");
        DecryText = new byte[byteText.length];
        rc4.init(byteKey);
        rc4.CryDecry(CryText, DecryText);
        str = new String(DecryText);
        System.out.print("\nРасшифрованный текст: ");
        System.out.println(str);
        if(str.equals(text))
            System.out.println("\nПоздравляем!\nРасшифровка проведена верно!");
    }
}
