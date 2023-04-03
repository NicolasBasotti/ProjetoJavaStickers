import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;


public class App {
    public static void main(String[] args) throws Exception {

        // Fazer uma conexão HTTP e buscar os top 250 filmes
        
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar os dados que nos interessam(titulo, poster, classificação)

        JsonParser parser = new JsonParser();
        List<Map<String, String>> ListaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        for(Map<String, String> filme : ListaDeFilmes){

            String urlImagem = filme.get("image");
            String nomeArquivo = filme.get("title") + ".png";
            InputStream inputStream = new URL(urlImagem).openStream();

            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);
            System.out.println("Título: " + (filme.get("title")));
            System.out.println();
        }
    }
}
