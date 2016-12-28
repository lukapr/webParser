import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pryaly on 12/28/2016.
 */
public class Main {
    private static final String BASE_URL = "https://www.wildberries.ru";
    private static final String ADD_URL = "/catalog/zhenshchinam/bele-i-kupalniki/plyazhnaya-moda?pagesize=40&sort=popular";

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("USE webparser.java [path to file with result] [number of products (<= 40)]");
            System.exit(0);
        }
        int nProducts = Integer.parseInt(args[1]);
        if (nProducts > 40) {
            System.out.println("Number of products should be <= 40");
            System.exit(0);
        }
        System.out.println("Start getting of " + nProducts + " products from " + BASE_URL + ADD_URL);
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(BASE_URL + ADD_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<Element> brandNames = doc.getElementsByAttributeValue("class", "cg_list_item");
        List<Product> products = brandNames.stream().map(Main::createProduct).collect(Collectors.toList());
        products = products.subList(0, Math.min(products.size(), nProducts));
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Start saving of products to " + args[0]);
        for (Product product : products)
            pw.println(product.toString());
        pw.close();

    }

    private static Product createProduct(Element e) {
        Product product = new Product();
        product.setHref(e.getElementsByAttribute("href").attr("href"));
        product.setName(e.getElementsByAttributeValue("class", "cg_c cg_brand").text());

        getCount(product.getHref(), product);

        return product;
    }

    private static void getCount(String url, Product p) {
        Document doc = null;
        try {
            doc = Jsoup.connect(BASE_URL + url).userAgent("Mozilla/5.0").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        p.setCount(doc.getElementsByAttributeValue("class", "j-orders-count").text());
        p.setArticle(doc.getElementsByAttributeValue("class", "article j-article").text());
    }
}
