package application.parser;

import datamodels.Product;
import datamodels.Statistics;
import datamodels.Restcount;
import datamodels.repositories.StatisticsRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParserStatistics {

    private static final String NO_PRODUCT = "Продан";
    private static final String NO_DATA = "Неизвестно";

    @Autowired
    private StatisticsRepository repository;

    public void parse(Product product) throws Exception {
        Statistics statistics = new Statistics();
        statistics.setProduct(product);
        Document doc = ParserHelper.getDocument(product.getHref());

        int ordersCount = Integer.parseInt(prepareString(doc.getElementsByClass("j-orders-count").text(), " "));

        statistics.setOrderscount(ordersCount);
        Double cost = Double.parseDouble(prepareString(doc.getElementById("Price").text(), "руб"));
        Double origCost = Double.parseDouble(prepareString(doc.getElementsByTag("del").text(), "руб"));
        statistics.setCost(cost);
        statistics.setOriginalcost(origCost == 0 ? cost : origCost);
        Double discount = Double.parseDouble(prepareString(doc.getElementsByClass("discount").text(), "%")
                .replaceAll("−", ""));
        statistics.setDiscount(discount);

        Elements sizes = doc.getElementsByClass("j-size");
        Set<Restcount> restcount = new HashSet<>();
        for (Element size : sizes) {
            String count = size.select(".goods-balance.j-low-quantity").text();
            boolean notAvailable = size.attr("class").equals("j-size disabled j-sold-out");
            restcount.add(new Restcount(size.getElementsByTag("span").get(0).text(),
                    notAvailable ? NO_PRODUCT : count.equals("") ? NO_DATA : count));
        }
        statistics.setRestcount(restcount);
        repository.save(statistics);
    }

    private String prepareString(String element, String regex) {
        final String result = element.replace(" ", " ").split(regex)[0].replaceAll(" ", "");
        return result.equals("") ? "0" : result;
    }
}
