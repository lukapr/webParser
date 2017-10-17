package springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configs")
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Config getConfiguration(@PathVariable("id") Long id) throws Exception {
        LOG.info("getConfiguration() with id = " + id);
        Config config = Optional.ofNullable(this.getRepository().findOne(id))
                .orElseThrow(() -> new Exception("Can't find configuration with ID = " + id));
        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteConfiguration(@PathVariable("id") Long id) throws Exception {
        LOG.info("deleteConfiguration() with id = " + id);
        this.getRepository().delete(id);
        return !getRepository().exists(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Config> listConfiguration() throws Exception {
        LOG.info("listConfiguration()");
        return this.getRepository().findAllByOrderByLastupdatedDesc();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long addConfiguration(@RequestBody Config config) {
        LOG.info("addConfiguration()");
        Config result = this.getRepository().save(config);
        return result.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Long updateConfiguration(@RequestBody Config resultConfig) {
        Config result = this.getRepository().save(resultConfig);
        return result.getId();
    }

    public ConfigRepository getRepository() {
        return repository;
    }
}
