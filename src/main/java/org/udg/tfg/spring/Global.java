package org.udg.tfg.spring;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.udg.tfg.spring.repository.ClientRepository;
import org.udg.tfg.spring.repository.PerruquerRepository;
import org.udg.tfg.spring.service.*;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
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
    ServeiPrestatService serveiPrestatService;

    @Autowired
    private
    ReservaService reservaService;


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
        logger.info("Iniciant la base de dades inicial ...");

        perruquerService.register("d", "David Tellez Lorenzo", "1");
        perruquerService.register("f", "Ferran Rodriguez Martinez", "1");

        serveiPrestatService.addServeiPrestat(9, "Corte");
        serveiPrestatService.addServeiPrestat(7, "Máquina");
        serveiPrestatService.addServeiPrestat(3, "Cejas");
        serveiPrestatService.addServeiPrestat(4, "Barba");

        producteService.addProducte(5,"Laca");
        producteService.addProducte(7,"Cera");
        producteService.addProducte(5,"Cera en polvo");
        producteService.addProducte(8,"Aceite barba");
        producteService.addProducte(8,"Gomina");


        Calendar cReserva = GregorianCalendar.getInstance();
        cReserva.set(Calendar.HOUR_OF_DAY, 9);
        cReserva.set(Calendar.MINUTE, 15);
        reservaService.addReserva("Carles Mallol Blai", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 9);
        cReserva.set(Calendar.MINUTE, 45);
        reservaService.addReserva("José Rodríguez Espinosa", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 10);
        cReserva.set(Calendar.MINUTE, 10);
        reservaService.addReserva("Xaro Martínez Herrera", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 10);
        cReserva.set(Calendar.MINUTE, 50);
        reservaService.addReserva("Arlet Martí Rodríguez", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 11);
        cReserva.set(Calendar.MINUTE, 30);
        reservaService.addReserva("David Téllez Lorenzo", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 12);
        cReserva.set(Calendar.MINUTE, 05);
        reservaService.addReserva("Àlex Martí Nuñez", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 16);
        cReserva.set(Calendar.MINUTE, 05);
        reservaService.addReserva("Marta Rodríguez Martínez", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 17);
        cReserva.set(Calendar.MINUTE, 00);
        reservaService.addReserva("Adrián Quesada Lóepz", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 17);
        cReserva.set(Calendar.MINUTE, 35);
        reservaService.addReserva("Davínia Jiménez", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 18);
        cReserva.set(Calendar.MINUTE, 15);
        reservaService.addReserva("Josefina Rodríguez Espinosa", (long) 1, cReserva.getTime());

        cReserva.set(Calendar.HOUR_OF_DAY, 19);
        cReserva.set(Calendar.MINUTE, 24);
        reservaService.addReserva("Manuel Rodríguez Méndez", (long) 1, cReserva.getTime());

        /*IdObject clientId = clientService.addClient("Un client", perruquer.getId(), new Date(), 2, 16, false);
        Producte producte = producteService.addProducte(14, "CERA PELO");
        clientService.addProductesToClient(perruquer.getId(), clientId.getId(), new ArrayList<Long>() {{
            add(producte.getId());
        }});*/


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
