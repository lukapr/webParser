import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
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
    private static final String NO_DATA = "Неизвестно";
    private static final String ZERO_DISCOUNT = "0 %";
    private static final String PAGE_SIZE = "pagesize=100";
    private static final String PAGE = "page=";

    private static Map<String, Product> products = new HashMap<>();

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("USE webparser.java [path to file with result] [link to first page of products]");
            System.exit(0);
        }
        String link = args[1];
        System.out.println("Start getting of products from " + link);
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(link + "?" + PAGE_SIZE).timeout(0).userAgent("Mozilla/5.0").get();
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
            }
            try {
                doc = Jsoup
                        .connect(link + "?" + PAGE_SIZE + "&" + PAGE + pageNumber).timeout(0).userAgent("Mozilla/5.0").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        File file = new File("C:\\tmp\\test.xml");

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
            Marshaller jaxbMarshaller;
            jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Result result = new Result(products.values(), link);
            jaxbMarshaller.marshal(result, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }




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
                doc = Jsoup.connect(url).timeout(0).userAgent("Mozilla/5.0").get();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            product.setOrdersCount(doc.getElementsByClass("j-orders-count").text());
            String cost = doc.getElementById("Price").text();
            product.setCost(cost);
            String origCost = doc.getElementsByTag("del").text();
            product.setOriginalCost(origCost.equals("") ? cost : origCost);
            String discount = doc.getElementsByClass("discount").text();
            product.setDiscount(discount.equals("") ? ZERO_DISCOUNT : discount);

            Elements sizes = doc.getElementsByClass("j-size");
            List<RestCount> restCount = new ArrayList<>();
            for (Element size : sizes) {
                String count = size.select(".goods-balance.j-low-quantity").text();
                boolean notAvailable = size.attr("class").equals("j-size disabled j-sold-out");
                restCount.add(new RestCount(size.getElementsByTag("span").get(0).text(),
                        notAvailable ? NO_PRODUCT : count.equals("") ? NO_DATA : count));
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
