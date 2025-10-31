
# Agrotech Integration System

Este proyecto simula un sistema de integración de datos agrícolas utilizando **Apache Camel**, **MySQL**, y procesamiento de archivos. Está compuesto por tres módulos principales que colaboran para transformar, almacenar y consultar lecturas de sensores en campo.

---
## Estructura del Proyecto
```
AgroAnalyzer/             ← Recibe JSON y lo inserta en MySQL
FieldControl/             ← Simula consultas RPC por sensor
SensData/                 ← Contiene archivos CSV con lecturas
src/main/java/com/agrotech/
├── App.java
├── model/SensorLectura.java
└── Routes/
    ├── SensDataRoute.java
    ├── AgroAnalyzerRoute.java
    └── FieldControlRoute.java
```

---

## Flujo de Datos

### SensData (CSV → JSON)

- **Entrada:** `SensData/sensores.csv`
- **Proceso:** Convierte CSV en JSON válido
- **Salida:** `AgroAnalyzer/sensores.json`

### AgroAnalyzer (JSON → MySQL)

- **Entrada:** `AgroAnalyzer/sensores.json`
- **Proceso:** Deserializa y guarda lecturas en MySQL
- **Tabla:** `lecturas(id, id_sensor, fecha, humedad, temperatura)`

### FieldControl (Consulta RPC simulada)

- **Entrada:** `FieldControl/requests/S001.txt`
- **Proceso:** Consulta la lectura más reciente del sensor
- **Salida:** `FieldControl/responses/S001-respuesta.json`

---

## Tecnologías utilizadas

- Apache Camel 4.4.0
- MySQL Connector/J
- Maven
- Java 17

---

## Cómo ejecutar

1. Asegúrate de tener MySQL corriendo y la base de datos `agrotech` creada.
2. Ejecuta:

```bash
mvn clean compile
mvn exec:java
```

3. Coloca un archivo CSV en `SensData/`
4. Coloca una consulta `.txt` en `FieldControl/requests/`
5. Verifica la respuesta en `FieldControl/responses/`

---

## Extensiones posibles

- Validación de sensores no encontrados
- Simulación de múltiples sensores
- Exportación de respuestas como CSV
- Integración con APIs REST reales



### Autor: WillyC4