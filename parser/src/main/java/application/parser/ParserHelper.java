package application.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ParserHelper {

    private static Logger logger = Logger.getLogger(ParserHelper.class);

    private static final int N_RETRIES = 3;
    private static final String USER_AGENT = "Mozilla/5.0";

    static final String BASE_URL = "https://www.wildberries.ru";
    static final String PAGE_SIZE = "pagesize=100";

    public static Document getDocument(String link) throws Exception {
        Document doc = null;
        IOException lastError = null;
        for (int i = 0; i < N_RETRIES; i++) {
            try {
                doc = Jsoup.connect(link).timeout(0).userAgent(USER_AGENT).get();
            } catch (IOException e) {
                logger.error("Exception during getting page: ", e);
                doc = null;
                lastError = e;
                logger.info("Exception during getting page: ", e);
                logger.info("Sleep 2 seconds");
                Thread.sleep(2000);
                logger.info("Try to get info again");
            }
            if (doc != null) {
                break;
            }
        }
        if (doc == null) {
            logger.error("LAST Exception during getting page: ", lastError);
            throw new Exception(lastError);
        }
        return doc;
    }
}
