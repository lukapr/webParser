package application.parser;

import datamodels.Category;
import datamodels.Product;
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
import java.util.List;

import static application.parser.ParserHelper.PAGE_SIZE;

@Service
public class ParserProduct {

    private static final String PAGE = "page=";

    private static Logger logger = Logger.getLogger(ParserProduct.class);

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

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
            //TODO we need to get products dipper (I mean go to each product on page, for each product check all possible colors)
            for (Element element : elements) {
                products.addAll(createProducts(element, products));
            }
        }
        category.addProducts(products);
        categoryRepository.save(category);
    }

    private List<Product> createProducts(Element element, List<Product> parsedProducts) {
        List<Product> products = new ArrayList<>();
        String[] articles = element.getElementsByAttribute("data-colors").attr("data-colors").split(",");
        for (String article : articles) {
            if (parsedProducts.stream().anyMatch(v -> v.getArticle().equals(article))) {
                continue;
            }
//            logger.info("Start getting info for product with article " + article);
            Product product;
            if (productRepository.findByArticle(article).size() > 0) {
                product = productRepository.findByArticle(article).get(0);
            } else {
                product = new Product();
                product.setArticle(article);
                String url = createUrl(article);
                product.setHref(url);
                product.setName(element.getElementsByClass("brand-name").text());
                String origImg = element.getElementsByClass("thumbnail").get(0).attr("data-original");
                product.setImg(origImg.equals("") ? element.getElementsByClass("thumbnail").get(0).attr("src") : origImg);
            }
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
