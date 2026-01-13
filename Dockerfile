FROM amazoncorretto:21
RUN mkdir -p /var/www/app
WORKDIR /var/www/app
ADD target/astropay-demo.jar .
ADD wait-for-it.sh .
RUN chmod +x wait-for-it.sh
CMD ["./wait-for-it.sh", "db:3306","--","java","-Dspring.profiles.active=production","-jar", "astropay-demo.jar"]
EXPOSE 8080
