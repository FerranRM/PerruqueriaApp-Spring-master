package org.udg.pds.springtodo;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.service.ClientService;
import org.udg.pds.springtodo.service.TagService;
import org.udg.pds.springtodo.service.TaskService;
import org.udg.pds.springtodo.service.PerruquerService;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class Global {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private MinioClient minioClient;

    private Logger logger = LoggerFactory.getLogger(Global.class);

    @Autowired
    private
    PerruquerService perruquerService;

    @Autowired
    private
    ClientService clientService;

    @Autowired
    private
    TaskService taskService;

    @Autowired
    private
    TagService tagService;

    @Value("${todospring.minio.url:}")
    private String minioURL;

    @Value("${todospring.minio.access-key:}")
    private String minioAccessKey;

    @Value("${todospring.minio.secret-key:}")
    private String minioSecretKey;

    @Value("${todospring.minio.bucket:}")
    private String minioBucket;

    @Value("${todospring.base-url:#{null}}")
    private String BASE_URL;

    @Value("${todospring.base-port:8080}")
    private String BASE_PORT;


    @PostConstruct
    void init() {

        logger.info(String.format("Starting Minio connection to URL: %s", minioURL));
        try {
            minioClient = new MinioClient(minioURL, minioAccessKey, minioSecretKey);
        } catch (Exception e) {
            logger.warn("Cannot initialize minio service with url:" + minioURL + ", access-key:" + minioAccessKey + ", secret-key:" + minioSecretKey);
        }

        if (minioBucket == "") {
            logger.warn("Cannot initialize minio bucket: " + minioBucket);
            minioClient = null;
        }

        if (BASE_URL == null) BASE_URL = "http://localhost";
        BASE_URL += ":" + BASE_PORT;

        initData();
    }

    private void initData() {
        logger.info("Starting populating database ...");
        Perruquer perruquer = perruquerService.register("fer", "fer@hotmail.com", "123");

        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();

        clientService.addClient("Ferran Rodriguez", 12, false,1,currentTime,perruquer.getId());
        clientService.addClient("David Tellez", 19, false,2,currentTime, perruquer.getId());

        IdObject taskId = taskService.addTask("Una tasca", perruquer.getId(), new Date(), new Date());
        Tag tag = tagService.addTag("ATag", "Just a tag");
        taskService.addTagsToTask(perruquer.getId(), taskId.getId(), new ArrayList<Long>() {{
            add(tag.getId());
        }});
        perruquerService.register("user", "user@hotmail.com", "0000");
    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

    public String getMinioBucket() {
        return minioBucket;
    }

    public String getBaseURL() {
        return BASE_URL;
    }
}
