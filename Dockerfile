FROM bellsoft/liberica-openjdk-debian:17

COPY ./build/libs/*SNAPSHOT.jar /appointMeAdminBot.jar

ENTRYPOINT ["java", "-jar", "appointMeAdminBot.jar"]