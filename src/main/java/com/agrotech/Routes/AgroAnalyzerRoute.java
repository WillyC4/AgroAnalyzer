package com.agrotech.routes;

import com.agrotech.model.SensorLectura;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class AgroAnalyzerRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("file:./AgroAnalyzer?noop=true")
            .filter(header("CamelFileName").isEqualTo("sensores.json"))
            .routeId("agroanalyzer-db")
            .log("[AgroAnalyzer] Procesando sensores.json")
            .unmarshal().json(JsonLibrary.Jackson, SensorLectura[].class)
            .split(body())
                .log("Insertando lectura: ${body.id_sensor} - ${body.fecha}")
                .setBody(simple(
                    "INSERT INTO lecturas (id_sensor, fecha, humedad, temperatura) " +
                    "VALUES ('${body.id_sensor}', '${body.fecha}', ${body.humedad}, ${body.temperatura})"
                ))
                .to("jdbc:miDataSource")
            .end();
    }
}