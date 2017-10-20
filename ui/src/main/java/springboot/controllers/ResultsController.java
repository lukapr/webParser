package springboot.controllers;

import datamodels.Category;
import datamodels.Product;
import datamodels.Statistics;
import datamodels.repositories.CategoryRepository;
import datamodels.repositories.ProductRepository;
import datamodels.repositories.StatisticsRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping("/results")
@Getter
public class ResultsController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> listResults() throws Exception {
        LOG.info("listResults()");
        return (List<Product>) this.getProductRepository().findAll();
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> listCategories() throws Exception {
        LOG.info("listCategories()");
        return (List<Category>) this.getCategoryRepository().findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Map<String, Integer> listOrders() throws Exception {
        LOG.info("listOrders()");
        final List<Product> products = (List<Product>) getProductRepository().findAll();
        final Map<String, Integer> collect = products.stream()
                .collect(groupingBy(Product::getName)).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().stream()
                        .mapToInt(p -> p.getStatistics().stream().mapToInt(Statistics::getOrderscount).sum()).sum()));
        return collect;

    }
}
