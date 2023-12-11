# Usar una imagen base de Java
FROM openjdk:8-jdk-alpine

# Variable para el directorio de la aplicación dentro del contenedor
ARG APP_DIR=/usr/app

# Crear el directorio de la aplicación
RUN mkdir -p $APP_DIR

# Establecer el directorio de la aplicación como el directorio de trabajo
WORKDIR $APP_DIR

# Copiar el archivo JAR de la aplicación al directorio de la aplicación
COPY build/libs/userapi-0.0.1-SNAPSHOT.jar $APP_DIR/userapi-0.0.1-SNAPSHOT.jar

# Comando para iniciar la aplicación
CMD ["java", "-jar", "userapi-0.0.1-SNAPSHOT.jar"]
