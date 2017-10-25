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
    public void parse(String link) throws Exception {
        final List<Category> categories = parserCategory.parse(link + "?" + PAGE_SIZE);
        for (Category category : categories) {
            final List<Category> categoryFromDb = getRepository().findByName(category.getName());
            //TODO need to add additional parameter to name
            Category currentCategory = categoryFromDb.isEmpty() ? category : categoryFromDb.get(0);
            parserProduct.parse(currentCategory);

            final List<Product> products = getProductRepository().findByCategoriesContains(currentCategory);

            for (Product product : products) {
                parserStatistics.parse(product);
            }
        }
    }
}
