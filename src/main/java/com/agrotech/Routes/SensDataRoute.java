package com.agrotech.routes;

import org.apache.camel.builder.RouteBuilder;
import java.util.ArrayList;
import java.util.List;

public class SensDataRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("file:./SensData?noop=true")
            .routeId("sensdata-transfer")
            .filter(header("CamelFileName").endsWith(".csv"))
            .log("[SensData] Procesando archivo: ${file:name}")
            .convertBodyTo(String.class)
            .process(exchange -> {
                String contenido = exchange.getIn().getBody(String.class);
                String[] lineas = contenido.split("\n");
                List<String> objetos = new ArrayList<>();

                for (int i = 0; i < lineas.length; i++) {
                    if (i == 0) continue; // omitir encabezado
                    String[] campos = lineas[i].split(",");
                    if (campos.length >= 4) {
                        String json = String.format(
                            "{\"id_sensor\":\"%s\",\"fecha\":\"%s\",\"humedad\":%s,\"temperatura\":%s}",
                            campos[0], campos[1], campos[2], campos[3]
                        );
                        objetos.add(json);
                    }
                }

                String resultado = "[\n" + String.join(",\n", objetos) + "\n]";
                exchange.getIn().setBody(resultado);
            })
            .to("file:./AgroAnalyzer?fileName=sensores.json")
            .log("[SensData] Archivo JSON generado en AgroAnalyzer.")
            .end();
    }
}