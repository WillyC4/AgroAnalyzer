package com.agrotech;

import com.agrotech.routes.SensDataRoute;
import com.agrotech.routes.AgroAnalyzerRoute;
import com.agrotech.routes.FieldControlRoute;
import org.apache.camel.main.Main;
import org.apache.commons.dbcp2.BasicDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class App {
    public static void main(String[] args) throws Exception {
        Main main = new Main();

        // Configurar el datasource
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/agrotech");
        ds.setUsername("root");
        ds.setPassword("");

        // Configurar Jackson con soporte para fechas
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JacksonDataFormat jsonFormat = new JacksonDataFormat();
        jsonFormat.setObjectMapper(mapper);

        // Registrar rutas y beans
        main.bind("miDataSource", ds);
        main.bind("jsonFormat", jsonFormat);
        main.configure().addRoutesBuilder(new SensDataRoute());
        main.configure().addRoutesBuilder(new AgroAnalyzerRoute());
        main.configure().addRoutesBuilder(new FieldControlRoute());

        // Ejecutar Camel
        main.run(args);
    }
}