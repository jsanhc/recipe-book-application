FROM openjdk:17-jdk-slim

LABEL org.jsanchez.image.authors="jsanchez"

ARG targetFile

RUN mkdir /app
RUN groupadd -r e-recipe && useradd -r -s /bin/false -g e-recipe e-recipe

WORKDIR /app
COPY ${targetFile} /app

RUN chown -R e-recipe:e-recipe /app
USER e-recipe

# shell form for environment variable expansion
ENTRYPOINT java -jar *.jar