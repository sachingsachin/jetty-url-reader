# jetty-url-reader

Run as `mvn jetty:run` and visit  [http://localhost:8080/url-input](http://localhost:8080/url-input)

You can also run the war file as:

```
mvn clean package
java -jar target/dependency/jetty-runner.jar target/*.war
```


Enter a complete URL (with http(s):// etc) for a successful fetch.


# Contains examples for:

1. $.get() for making AJAX requests.

2. Apache Commons IO for URL reading.

3. ObjectMapper for getting Post and Get params.
