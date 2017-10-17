package springboot;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.rabbit.Producer;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/configs")
@Getter
public class ConfigController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Producer producer;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Config getConfiguration(@PathVariable("id") Long id) throws Exception {
        LOG.info("getConfiguration() with id = " + id);
        Config config = Optional.ofNullable(this.getConfigRepository().findOne(id))
                .orElseThrow(() -> new Exception("Can't find configuration with ID = " + id));
        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteConfiguration(@PathVariable("id") Long id) throws Exception {
        LOG.info("deleteConfiguration() with id = " + id);
        this.getConfigRepository().delete(id);
        return !getConfigRepository().exists(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Config> listConfiguration() throws Exception {
        LOG.info("listConfiguration()");
        return this.getConfigRepository().findAllByOrderByLastupdatedDesc();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long addConfiguration(@RequestBody Config config) {
        LOG.info("addConfiguration()");
        Config result = this.getConfigRepository().save(config);
        return result.getId();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Long updateConfiguration(@RequestBody Config resultConfig) {
        Config result = this.getConfigRepository().save(resultConfig);
        return result.getId();
    }

    @RequestMapping(value = "/process/{id}", method = RequestMethod.POST)
    public long processConfiguration(@PathVariable("id") Long id) throws Exception {
        LOG.info("processConfiguration() with id = " + id);
        Config config = Optional.ofNullable(this.getConfigRepository().findOne(id))
                .orElseThrow(() -> new Exception("Can't find configuration with ID = " + id));
        Task task = new Task();
        task.setConfig(config);
        task.setStatus(Status.CREATED.name());
        Task result = taskRepository.save(task);
//        RMQMessage
        producer.sendMessage(config.getLink());
        result.setStatus(Status.PENDING.name());
        taskRepository.save(task);
        return task.getId();
    }

    private ConfigRepository getConfigRepository() {
        return configRepository;
    }
}
