package springboot.controllers;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springboot.datamodels.Task;
import springboot.datamodels.TaskRepository;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Getter
public class TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable("id") Long id) throws Exception {
        LOG.info("getConfiguration() with id = " + id);
        return Optional.ofNullable(this.getTaskRepository().findOne(id))
                .orElseThrow(() -> new Exception("Can't find task with ID = " + id));
    }
}
