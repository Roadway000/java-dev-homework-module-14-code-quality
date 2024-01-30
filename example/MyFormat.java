package org.example;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyFormat {
    public static Logger logger = null;
    static {
        InputStream stream = MyFormat.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger= Logger.getLogger(MyFormat.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //System.out.printf("%2$s | %3$1.4f | %1$,d", 1333, "hello", 5.4444);
        logger.info("in MyClass");
        logger.warning("a test warning");
    }
}
