# maven:3.8.3-openjdk-8
FROM openjdk:8
ARG ENV
RUN mvn clean compile package -DskipTests -P$ENV -Dspring.profiles.active=$ENV;

# maven:3.8.3-openjdk-8
FROM openjdk:8
EXPOSE 8181
ENV SERVICE="transactions_routine"
ADD rest/target/*.jar ${SERVICE}.jar
ENTRYPOINT ["java", "-jar", "$JAVA_OPTS", "/$SERVICE.jar"]