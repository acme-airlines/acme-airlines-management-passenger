# Usa una imagen base de JDK para ejecutar el JAR
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/passenger-0.0.1-SNAPSHOT.jar passenger-ms.jar

EXPOSE 8001

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "passenger-ms.jar"]