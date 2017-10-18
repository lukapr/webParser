package application.parser;

import datamodels.Category;
import datamodels.repositories.CategoryRepository;
import datamodels.Product;
import datamodels.repositories.ProductRepository;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static application.parser.ParserHelper.PAGE_SIZE;

@Service
@Data
public class ParserImpl {

    private static final String NO_PRODUCT = "Продан";
    private static final String NO_DATA = "Неизвестно";
    private static final String ZERO_DISCOUNT = "0 %";
    private static final String PAGE = "page=";

    private static final Set<String> fullArticles = new HashSet<>();

    static Logger logger = Logger.getLogger(ParserImpl.class);

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ParserCategory parserCategory;
    @Autowired
    ParserProduct parserProduct;
    @Autowired
    ParserStatistics parserStatistics;

//    @Transactional
    public boolean parse(String link) throws Exception {
        final List<Category> categories = parserCategory.parse(link + "?" + PAGE_SIZE);
        for (Category category : categories) {
            final Category currentCategory = Optional.ofNullable(getRepository().findByName(category.getName()).get(0))
                    .orElseThrow(() -> new Exception("Not able to find category with name: " + category.getName()));
            parserProduct.parse(currentCategory);

            final List<Product> products = getProductRepository().findByCategoriesContains(currentCategory);

//            final Category currentCategoryWithProducts = Optional.ofNullable(getRepository()
//                    .findByName(category.getName()).get(0))
//                    .orElseThrow(() -> new Exception("Not able to find category with name: " + category.getName()));
            for (Product product : products) {
                parserStatistics.parse(product);
            }
        }
        return true;
    }

//    public boolean parse(String link) throws Exception {
//
//
////        prepareLogger(args[0]);
//        logger.info("Start getting of products from " + link);
//
//        Document doc = getDoc(link + "?" + PAGE_SIZE);
//
//        Elements childs = doc.select(".selected.hasnochild");
//        List<Category> categoriesList = new ArrayList<>();
//        if (childs.size() > 0) {
//            Elements ul = childs.get(0).getElementsByTag("ul");
//            Elements categories = ul.size() > 0 ? ul.get(0).getElementsByTag("a") : doc.select(".selected.hasnochild").get(0).children();
//
//            for (Element categoryElement : categories) {
//
//                categoriesList.add(createCategory(categoryElement.text(), BASE_URL + categoryElement.attr("href")));
//            }
//        } else {
//            categoriesList.add(createCategory(doc.getElementById("catalog-content").getElementsByTag("h1").get(0).text(), link));
//        }
//
//        getRepository().save(categoriesList);
//        fullArticles.clear();
//        return true;
//
//
////        File file = new File(args[0]);
////
////        try {
////            JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
////            Marshaller jaxbMarshaller;
////            jaxbMarshaller = jaxbContext.createMarshaller();
////
////            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
////            Date d = new Date();
////            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
////            Result result = new Result(categoriesList.stream().filter(v -> v.getProducts() != null)
////                    .collect(Collectors.toList()), link, format1.format(d));
////            jaxbMarshaller.marshal(result, file);
////        } catch (JAXBException e) {
////            logger.error("Error during generating xml: ", e);
////        }
//    }
//
//    private static Category createCategory(String name, String link) throws Exception {
//        Category category = new Category();
//        category.setName(name);
//        String href = link;
//        Document doc = getDoc(href + "?" + PAGE_SIZE);
//
//        if (doc.getElementById("divGoodsNotFound") != null) {
//            return category;
//        }
//
//        int numberOfPages = (int) Math.ceil(Integer.parseInt(((TextNode) doc.select(".total.many").get(0).childNode(1)
//                .childNode(0)).text()) / 100.0);
//        for (int pageNumber = 1; pageNumber <= numberOfPages; pageNumber++) {
//            doc = getDoc(href + "?" + PAGE_SIZE + "&" + PAGE + pageNumber);
//            Elements elements = doc.getElementsByClass("dtList");
//            for (Element element : elements) {
//                category.addProducts(createProducts(element));
//            }
//        }
//        return category;
//    }
//
////    private static void prepareLogger(String fileName) {
////        // creates pattern layout
////        PatternLayout layout = new PatternLayout();
////        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
////        layout.setConversionPattern(conversionPattern);
////
////        // creates console appender
////        ConsoleAppender consoleAppender = new ConsoleAppender();
////        consoleAppender.setLayout(layout);
////        consoleAppender.activateOptions();
////
////        // creates file appender
////        FileAppender fileAppenderError = new FileAppender();
////        fileAppenderError.setFile(fileName + "errorlogs.txt");
////        fileAppenderError.setLayout(layout);
////        fileAppenderError.activateOptions();
////
//////        FileAppender fileAppender = new FileAppender();
//////        fileAppender.setFile(fileName + "infologs.txt");
//////        fileAppender.setLayout(layout);
//////        fileAppender.activateOptions();
////
////        // configures the root logger
////        Logger rootLogger = Logger.getRootLogger();
////        rootLogger.setLevel(Level.ERROR);
////        rootLogger.addAppender(consoleAppender);
//////        rootLogger.addAppender(fileAppender);
////        rootLogger.addAppender(fileAppenderError);
////
////        // creates a custom logger and log messages
////        logger = Logger.getLogger(Main.class);
////    }
//
//    private static Document getDoc(String link) throws Exception {
//        Document doc = null;
//        IOException lastError = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                doc = Jsoup.connect(link).timeout(0).userAgent("Mozilla/5.0").get();
//            } catch (IOException e) {
//                logger.error("Exception during getting page: ", e);
//                doc = null;
//                lastError = e;
//                logger.info("Exception during getting page: ", e);
//                logger.info("Sleep 2 seconds");
//                Thread.sleep(2000);
//                logger.info("Try to get info again");
//            }
//            if (doc != null) {
//                break;
//            }
//        }
//        if (doc == null) {
//            logger.error("LAST Exception during getting page: ", lastError);
//            throw new Exception(lastError);
//        }
//        return doc;
//    }
//
//    protected static List<Product> createProducts(Element element) throws Exception {
//        List<Product> products = new ArrayList<>();
//        String[] articles = element.getElementsByAttribute("data-colors").attr("data-colors").split(",");
//        for (String article : articles) {
//            logger.info("Start getting info for product with article " + article);
//            if (fullArticles.contains(article)) {
//                logger.info("Product with this article already extracted");
//                continue;
//            }
//            fullArticles.add(article);
//            Product product = new Product();
//            product.setArticle(article);
//            String url = createUrl(article);
////            product.setHref(url);
//            product.setName(element.getElementsByClass("brand-name").text());
//
//            Document doc = getDoc(url);
//            product.setHref(doc.getElementsByAttributeValue("itemprop", "image").get(0).attr("content"));
//
//            product.setOrderscount(doc.getElementsByClass("j-orders-count").text());
//            String cost = doc.getElementById("Price").text();
//            product.setCost(cost);
//            String origCost = doc.getElementsByTag("del").text();
//            product.setOriginalcost(origCost.equals("") ? cost : origCost);
//            String discount = doc.getElementsByClass("discount").text();
//            product.setDiscount(discount.equals("") ? ZERO_DISCOUNT : discount);
//
//            Elements sizes = doc.getElementsByClass("j-size");
//            List<Restcount> restcount = new ArrayList<>();
//            for (Element size : sizes) {
//                String count = size.select(".goods-balance.j-low-quantity").text();
//                boolean notAvailable = size.attr("class").equals("j-size disabled j-sold-out");
//                restcount.add(new Restcount(size.getElementsByTag("span").get(0).text(),
//                        notAvailable ? NO_PRODUCT : count.equals("") ? NO_DATA : count));
//            }
//            product.setRestcounts(restcount);
//            products.add(product);
//        }
//        return products;
//    }
//
//    private static String createUrl(String color) {
//        final String CATALOG_URL = "https://www.wildberries.ru/catalog/";
//        final String DETAIL_URL = "/detail.aspx";
//        return CATALOG_URL + color + DETAIL_URL;
//    }
}
