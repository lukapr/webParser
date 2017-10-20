package application.parser;

import datamodels.Category;
import datamodels.Product;
import datamodels.Restcount;
import datamodels.repositories.CategoryRepository;
import datamodels.repositories.ProductRepository;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static application.parser.ParserHelper.PAGE_SIZE;

@Service
public class ParserProduct {

    private static final String PAGE = "page=";

    private static Logger logger = Logger.getLogger(ParserProduct.class);

    @Autowired
    private ProductRepository repository;

    public void parse(Category category) throws Exception {
        String href = category.getHref();
        Document doc = ParserHelper.getDocument(href + "?" + PAGE_SIZE);

        List<Product> products = new ArrayList<>();
        if (doc.getElementById("divGoodsNotFound") != null) {
            return;
        }
        int numberOfPages = (int) Math.ceil(Integer.parseInt(((TextNode) doc.select(".total.many").get(0).childNode(1)
                .childNode(0)).text()) / 100.0);
        for (int pageNumber = 1; pageNumber <= numberOfPages; pageNumber++) {
            doc = ParserHelper.getDocument(href + "?" + PAGE_SIZE + "&" + PAGE + pageNumber);
            Elements elements = doc.getElementsByClass("dtList");
            for (Element element : elements) {
                products.addAll(createProducts(element, category));
            }
        }
        repository.save(products);
    }

    private List<Product> createProducts(Element element, Category category) {
        List<Product> products = new ArrayList<>();
        String[] articles = element.getElementsByAttribute("data-colors").attr("data-colors").split(",");
        for (String article : articles) {
//            logger.info("Start getting info for product with article " + article);
            Product product;
            if (repository.findByArticle(article).size() > 0) {
                product = repository.findByArticle(article).get(0);
            } else {
                product = new Product();
                product.setArticle(article);
                String url = createUrl(article);
                product.setHref(url);
                product.setName(element.getElementsByClass("brand-name").text());
                //TODO check it! Not working for many products
                product.setImg(element.getElementsByClass("thumbnail").get(0).attr("src"));
            }
            product.addCategory(category);
            products.add(product);
        }
        return products;
    }

    private static String createUrl(String color) {
        final String CATALOG_URL = "https://www.wildberries.ru/catalog/";
        final String DETAIL_URL = "/detail.aspx";
        return CATALOG_URL + color + DETAIL_URL;
    }
}
