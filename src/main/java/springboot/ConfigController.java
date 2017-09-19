package springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping( "/config" )
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger( ConfigController.class );

    @Autowired
    private ConfigRepository   repository;

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public String getConfiguration(@PathVariable( "id" ) Long id) throws Exception {
        LOG.info("getConfiguration() with id = " + id);
        Config config = Optional.ofNullable(this.getRepository().findOne(id))
                .orElseThrow(() -> new Exception("Can't find configuration with ID = " + id));
        LOG.info("Link for config = " + config.getLink());
        return config.getLink();
    }

    public ConfigRepository getRepository() {
        return repository;
    }
}
