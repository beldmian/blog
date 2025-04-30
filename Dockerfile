FROM clojure:tools-deps-bullseye-slim AS builder
WORKDIR /opt/app
COPY . .
RUN make build-uberjar

FROM eclipse-temurin:24-jre-alpine

WORKDIR /opt/app
COPY --from=builder /opt/app/target/app.jar /opt/app/app.jar
EXPOSE 80
ENV PORT 80
ENTRYPOINT ["java", "-cp", "/opt/app/app.jar", "clojure.main", "-m", "server.core"]
