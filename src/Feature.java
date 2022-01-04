import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Feature {
    public static void main(String[] args) {
        featuresJava11();
    }

    public static void featuresJava11() {
        System.out.println("-Features Java 11");

//        java11StringApi();
        java11HttpApi();
    }

    private static void java11StringApi() {
        System.out.println("--String API");

        System.out.println("Trim");
        String s = " dev aaa \t\t ";
        System.out.println("***" + s + "***");

        String s1 = s.trim();
        System.out.println("***" + s1 + "***");
        System.out.println("---------------");

        System.out.println("Repeat");
        String s2 = "Abc";
        System.out.println(s2.repeat(3));
        System.out.println("---------------");

        System.out.println("Strip");
        String s3 = " Lorem Ipsum ";
        System.out.println(s3);
        System.out.println(s3.strip());
        System.out.println(s3.stripLeading());
        System.out.println(s3.stripTrailing());
        System.out.println("---------------");

        System.out.println("Is blank");
        String s4 = "";
        String s5 = "Loren";
        System.out.println(s4.isBlank());
        System.out.println(s5.isBlank());
        System.out.println("---------------");

        System.out.println("Lines");
        String s6 = "Lorem\nIpsum\nis simply\ndummy text";
        s6.lines().forEach(System.out::println);
        System.out.println("---------------");

        System.out.println("To array");
        List<String> stringList = new ArrayList<>();
        stringList.add("Lorem");
        stringList.add("Ipsum");
        stringList.add("Is simply");
        String[] stringArray1 = stringList.toArray(new String[stringList.size()]); //Before
        String[] stringArray2 = stringList.toArray(String[]::new);//Java 11
        System.out.println(Arrays.toString(stringArray1));
        System.out.println(Arrays.toString(stringArray2));
        System.out.println("---------------");
    }

    private static void java11HttpApi() {
        System.out.println("--HTTP client API");

        synchronousRequest();
        asynchronousRequest();
    }

    private static void synchronousRequest() {
        System.out.println("---Synchronous request");
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        try {
            var urlEndpoint = "https://postman-echo.com/get";//Var since java 10
            String params = "?foo1=bar1&foo2=bar2";
            URI uri = URI.create(urlEndpoint + params);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Headers: " + response.headers().allValues("content-type"));
            System.out.println("Body: " + response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void asynchronousRequest() {
        System.out.println("---Asynchronous request");
        List<URI> uris = Stream.of(
                "https://www.google.com/",
                "https://www.github.com/",
                "https://www.yahoo2.com/"
        ).map(URI::create).toList();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        CompletableFuture[] futures = uris.stream()
                .map(uri -> verifyUri(httpClient, uri))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();
    }

    private static CompletableFuture<Void> verifyUri(HttpClient httpClient, URI uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(5))
                .uri(uri)
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenApply(statusCode -> statusCode == 200)
                .exceptionally(ex -> false)
                .thenAccept(valid -> {
                    if (valid) {
                        System.out.println("[SUCCESS] Verified " + uri);
                    } else {
                        System.out.println("[FAILURE] Could not " + "verify " + uri);
                    }
                });
    }
}
