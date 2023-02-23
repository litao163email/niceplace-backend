FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
#几乎所有的java项目都可以用这个，只需要改niceplace-backend名字就好了
CMD ["java","-jar","/app/target/niceplace-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]