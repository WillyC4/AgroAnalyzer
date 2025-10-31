package com.agrotech.routes;

import org.apache.camel.builder.RouteBuilder;

public class FieldControlRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("file:./FieldControl/requests?noop=true")
            .routeId("fieldcontrol-rpc")
            .log("[FieldControl] Consulta recibida: ${file:name}")
            .convertBodyTo(String.class)
            .setHeader("sensorId", simple("${body}"))
            .setBody(simple(
                "SELECT * FROM lecturas WHERE id_sensor='${header.sensorId}' ORDER BY fecha DESC LIMIT 1"
            ))
            .to("jdbc:miDataSource")
            .marshal("jsonFormat") // ← usa el bean con soporte para LocalDateTime
            .toD("file:./FieldControl/responses?fileName=${header.sensorId}-respuesta.json")
            .log("[FieldControl] Respuesta enviada para ${header.sensorId}");
    }
}