package org.udg.tfg.spring;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.udg.tfg.spring.entity.*;
import org.udg.tfg.spring.repository.ClientRepository;
import org.udg.tfg.spring.repository.PerruquerRepository;
import org.udg.tfg.spring.service.*;
import org.udg.tfg.spring.entity.IdObject;
import org.udg.tfg.spring.entity.Perruquer;
import org.udg.tfg.spring.service.*;

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
    private
    TallCabellsService tallCabellsService;


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

        perruquerService.register("dtellez", "David Tellez Lorenzo", "1234");

        tallCabellsService.addTallCabells(9, "Corte");
        tallCabellsService.addTallCabells(7, "Máquina");
        tallCabellsService.addTallCabells(3, "Cejas");
        tallCabellsService.addTallCabells(4, "Barba");

        producteService.addProducte(5,"Laca");
        producteService.addProducte(7,"Cera");
        producteService.addProducte(5,"Cera en polvo");
        producteService.addProducte(8,"Aceite barba");
        producteService.addProducte(8,"Gomina");



        /*IdObject clientId = clientService.addClient("Un client", perruquer.getId(), new Date(), 2, 16, false);
        Producte producte = producteService.addProducte(14, "CERA PELO");
        clientService.addProductesToClient(perruquer.getId(), clientId.getId(), new ArrayList<Long>() {{
            add(producte.getId());
        }});*/




        perruquerService.register("ferran", "Ferran Rodriguez Martinez", "1234");
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
