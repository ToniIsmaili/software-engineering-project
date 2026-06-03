# Build WAR
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

# Run on Tomcat 10 + Java 25
FROM eclipse-temurin:25-jre-jammy

ENV CATALINA_HOME=/opt/tomcat
ENV PATH="${CATALINA_HOME}/bin:${PATH}"

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && curl -fsSL https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.34/bin/apache-tomcat-10.1.34.tar.gz \
       | tar -xzC /opt \
    && mv /opt/apache-tomcat-10.1.34 "${CATALINA_HOME}" \
    && chmod +x "${CATALINA_HOME}/bin/"*.sh \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/software-engineering-project.war "${CATALINA_HOME}/webapps/"

EXPOSE 8080

CMD ["catalina.sh", "run"]
