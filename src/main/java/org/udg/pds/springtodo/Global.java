package org.udg.pds.springtodo;

import io.minio.MinioClient;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.repository.ClientRepository;
import org.udg.pds.springtodo.repository.PerruquerRepository;
import org.udg.pds.springtodo.repository.ProducteRepository;
import org.udg.pds.springtodo.service.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

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
    PerruquerRepository perruquerRepository;

    @Autowired
    private
    ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private
    ProducteService producteService;

    @Autowired
    private ProducteRepository producteRepository;

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

        Date currentTime = Calendar.getInstance().getTime();

        Producte producte = new Producte("Crema para la cara",8);
        Producte producte2 = new Producte("Laca para el pelo",5);
        producteService.addProducte("Cera para el pelo",14);
        producteService.addProducte("Cera en polvo para el pelo",12);
        producteService.addProducte("Champú especial",8);
        producteService.addProducte("Aceite para la barba",7);
        producteService.addProducte("Gomina para el pelo",9);

        Perruquer perruquer = perruquerService.register("fer", "fer@hotmail.com", "123");


        Client client = new Client("Ferran Rodriguez", 12, false,1,currentTime);
        //Client client2 = new Client("David Tellez", 19, false,2,currentTime);


        Set<Producte> aaa = new HashSet<Producte>();
        aaa.add(producte);
        //aaa.add(producte2);
        client.setProductes(aaa);
        clientRepository.save(client);


        Set<Client> aaa1 = new HashSet<Client>();
        aaa1.add(client);
        producte.setClients(aaa1);
        producte2.setClients(aaa1);
        producteRepository.save(producte);
        producteRepository.save(producte2);











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
