FROM maven:3.8.2-openjdk-17 as BUILD
WORKDIR /app
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run