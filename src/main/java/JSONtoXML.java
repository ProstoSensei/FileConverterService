import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JSONtoXML
{
    public static void ConvertToXML(String jsonPath, String xmlPath) throws Exception
    {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(jsonPath))
        {
            JsonElement json = gson.fromJson(reader, JsonElement.class);
            String jsonStr = gson.toJson(json);
            JSONObject jsonObject = new JSONObject(jsonStr);

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("films");
            document.appendChild(root);

            for (String key : jsonObject.keySet())
            {
                JSONObject company = jsonObject.getJSONObject(key);
                JSONArray films = company.getJSONArray("films");
                for (int i = 0; i < films.length(); i++)
                {
                    JSONObject film = films.getJSONObject(i);
                    Element filmElement = document.createElement("film");
                    filmElement.setAttribute("id", String.valueOf(film.getInt("id")));
                    filmElement.setAttribute("filmCompany", key);

                    Element name = document.createElement("name");
                    name.appendChild(document.createTextNode(film.getString("name")));
                    filmElement.appendChild(name);

                    Element director = document.createElement("director");
                    director.appendChild(document.createTextNode(film.getString("director")));
                    filmElement.appendChild(director);

                    Element averageRating = document.createElement("averageRating");
                    averageRating.appendChild(document.createTextNode(String.valueOf(film.getInt("averageRating"))));
                    filmElement.appendChild(averageRating);

                    Element duration = document.createElement("duration");
                    duration.appendChild(document.createTextNode(String.valueOf(film.getInt("duration"))));
                    filmElement.appendChild(duration);

                    root.appendChild(filmElement);
                }
            }

            // Создание TransformerFactory для печати содержимого документа в XML файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Создание источника для преобразования
            DOMSource source = new DOMSource(document);
            // Выходной поток для файла
            StreamResult result = new StreamResult(new java.io.File(xmlPath));
            // Запись данных в файл
            transformer.transform(source, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}