import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pryaly on 12/28/2016.
 */
public class Main {
    private static final String BASE_URL = "https://www.wildberries.ru";

    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(BASE_URL + "/catalog/zhenshchinam/bele-i-kupalniki/plyazhnaya-moda?pagesize=40&sort=popular").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<Element> brandNames = doc.getElementsByAttributeValue("class", "cg_list_item");
        List<Product> products = brandNames.stream().map(Main::createProduct).collect(Collectors.toList());

    }

    private static Product createProduct(Element e) {
        Product product = new Product();
        product.setHref(e.getElementsByAttribute("href").attr("href"));
        product.setName(e.getElementsByAttributeValue("class", "cg_c cg_brand").text());
        try {
            getCount(product.getHref());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println(product);
        return product;
    }

    private static int getCount(String url) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(BASE_URL + url).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        doc.getElementById("product_articul").text();
        System.out.println(doc.toString());

        HttpGet req = new HttpGet(BASE_URL + url);
//        req.setHeader("User-Agent", DEFAULT_USER_AGENT);
        CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(req);
            InputStream inputStream = response.getEntity().getContent();
            IOUtils.toString(inputStream);


        return 0;
    }
}
