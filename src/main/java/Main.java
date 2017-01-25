import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pryaly on 12/28/2016.
 */
public class Main {
    private static final String BASE_URL = "https://www.wildberries.ru";
    private static final String NO_PRODUCT = "Продан";

    private static Map<String, Product> products = new HashMap<>();

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("USE webparser.java [path to file with result] [link to first page of products]");
            System.exit(0);
        }
        String link = args[1] + "?pagesize=100";
        System.out.println("Start getting of products from " + link);
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(link).userAgent("Mozilla/5.0").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int numberOfPages = (int) Math.floor(Integer.parseInt(((TextNode) doc.select(".total.many").get(0).childNode(1)
                .childNode(0)).text()) / 100.0);
        for (int pageNumber = 1; pageNumber <= numberOfPages; pageNumber++) {
            Elements elements = doc.getElementsByClass("dtList");
            for (Element element : elements) {
                createProducts(element);
                if (products.size() >= 25){
                    break;
                }
            }
            if (products.size() >= 25){
                break;
            }
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Start saving of products to " + args[0]);
        for (Product product : products.values())
            pw.println(product.toString());
        pw.close();



    }

    protected static void createProducts(Element element) {
        String[] articles = element.getElementsByAttribute("data-colors").attr("data-colors").split(",");
        for (String article : articles) {
            System.out.println("Start getting info for product with article " + article);
            if (products.containsKey(article)) {
                System.out.println("Product with this article already extracted");
                continue;
            }
            Product product = new Product();
            product.setArticle(article);
            String url = createUrl(article);
            product.setHref(url);
            product.setName(element.getElementsByClass("brand-name").text());

            Document doc = null;
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            product.setOrdersCount(doc.getElementsByClass("j-orders-count").text());
            product.setCost(doc.getElementById("Price").text());
            product.setOriginalCost(doc.getElementsByTag("del").text());
            product.setDiscount(doc.getElementsByClass("discount").text());

            Elements sizes = doc.getElementsByClass("j-size");
            Map<String, String> restCount = new HashMap<>();
            for (Element size : sizes) {
                restCount.put(size.getElementsByTag("span").get(0).text(),
                        size.select(".goods-balance.j-low-quantity").text());
            }
            Elements soldSizes = doc.select(".j-size.disabled.j-sold-out");
            for (Element size : soldSizes) {
                restCount.put(size.getElementsByTag("span").get(0).text(),
                        NO_PRODUCT);
            }
            product.setRestCount(restCount);
            products.put(article, product);
        }

    }

    private static String createUrl(String color) {
        final String CATALOG_URL = "https://www.wildberries.ru/catalog/";
        final String DETAIL_URL = "/detail.aspx";
        return CATALOG_URL + color + DETAIL_URL;
    }

}
