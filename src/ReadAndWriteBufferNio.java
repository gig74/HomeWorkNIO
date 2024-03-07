import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

public class ReadAndWriteBufferNio {

//    private static File in = new File(".\\resources\\in.txt");
//    private static File out = new File(".\\resources\\out.txt");
//    public static void main(String[] args) throws IOException {
//        readAndWrite(in, out) ;
//    }
    public static void readAndWrite(File in, File out) throws IOException {
        // Открываем два канала
        try (FileChannel inChannel = new FileInputStream(in).getChannel();
             FileChannel outChannel = new FileOutputStream(out).getChannel() ) {
            // ЧАСТЬ 1 !!! СОБИРАЕМ С ДОП ОБРАБОТКОЙ ВСЁ СОДЕРЖИМОЕ ФАЙЛА В ОДИН ПОТОК ByteArrayOutputStream И ПЕЧАТАЕМ ЕГО !!!
            // Пробуем собрать всё в один поток
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String text_1 = "Hello Wolrd!";
            byte[] buffer_1 = text_1.getBytes();
            String text_2 = "Добрый день !!! \n";
            byte[] buffer_2 = text_2.getBytes();
            byteArrayOutputStream.write(buffer_1);
            byteArrayOutputStream.write(buffer_2);

            int bufferSize = 512; // Задаём начальное значение для буфера
            if (bufferSize > inChannel.size()) {  // Если начальное значените превышает умольчальное дя канала
                bufferSize = (int) inChannel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize); // "Нарезаем" место для нового байт-буфера (FileChannel как бы любит именно в ByteBuffer писать)

            while (inChannel.read(buff) > 0) {
                byteArrayOutputStream.write(buff.array(),0,buff.position()) ; // С 0 и до позиции потому что в конце надо прочитать "остаточек" , а не полный размер
                buff.clear();
            }
            System.out.println(" Итого: " + byteArrayOutputStream.toString()  ); // Выводим то , что получилось  //+ " inChannel.size() " + inChannel.size()

            // ЧАСТЬ 2 !!!! ЗАНОСИМ НЕЧТО В БАЙТ БУФЕР И ЗАПИХИВАЕМ В ФАЙЛ КАНАЛ
            ByteBuffer buff_2 = ByteBuffer.wrap("Hello Wolrd 123!Добрый день 123!!! ".getBytes(StandardCharsets.UTF_8));

            outChannel.write(buff_2); // Заносим в файл с самого начала

            // А здесь мы переделываем выходной поток во входной и записываем в файл, причём по умольчанию в конец файла
            OutputStream outputStream = Channels.newOutputStream(outChannel);
            outputStream.write(byteArrayOutputStream.toByteArray());
        }
        catch ( IOException e ) {
            throw e;
        }

    }


}
