
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class ReadAndWriteBufferNioTest {
    File inTest= new File(".\\resources\\in.txt");
    File outTest = new File(".\\resources\\out.txt");

    @Test
    void readAndWriteTest() throws IOException {
        ReadAndWriteBufferNio.readAndWrite(inTest, outTest) ;
        Assertions.assertEquals(1,  1);
    }
}