FROM openjdk:8-jdk-alpine
RUN addgroup -S schodev && adduser -S vk -G schodev
USER vk:schodev

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

USER root
COPY src/main/resources/allPlans.json /app
CMD ["/bin/sh", 'chmod +w /app/allPlans.json']
ENTRYPOINT ["java","-cp","app:app/lib/*","com.vk.planner.Application"]
