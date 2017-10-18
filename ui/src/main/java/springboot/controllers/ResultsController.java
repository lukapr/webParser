package springboot.controllers;

import datamodels.Category;
import datamodels.Product;
import datamodels.repositories.CategoryRepository;
import datamodels.repositories.ProductRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springboot.datamodels.Config;

import java.util.List;

@RestController
@RequestMapping("/results")
@Getter
public class ResultsController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ProductRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> listResults() throws Exception {
        LOG.info("listResults()");
        return (List<Product>) this.getRepository().findAll();
    }
}
