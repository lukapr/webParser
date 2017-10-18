package application.parser;

import datamodels.Category;
import datamodels.repositories.CategoryRepository;
import lombok.Data;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ParserCategory {

    @Autowired
    private CategoryRepository repository;

    public List<Category> parse(String link) throws Exception {
        final Document document = ParserHelper.getDocument(link);
        Elements childs = document.select(".selected.hasnochild");
        List<Category> categoriesList = new ArrayList<>();
        if (childs.size() > 0) {
            Elements ul = childs.get(0).getElementsByTag("ul");
            Elements categories = ul.size() > 0 ? ul.get(0).getElementsByTag("a") :
                    document.select(".selected.hasnochild").get(0).children();

            for (Element categoryElement : categories) {
                categoriesList.add(new Category(categoryElement.text(), ParserHelper.BASE_URL +
                        categoryElement.attr("href")));
            }
        } else {
            categoriesList.add(new Category(document.getElementById("catalog-content").getElementsByTag("h1")
                    .get(0).text(), link));
        }
        categoriesList.forEach(category -> {
            if(repository.findByName(category.getName()).size() == 0) {
                repository.save(category);
            }
        });
        return categoriesList;
    }
}
