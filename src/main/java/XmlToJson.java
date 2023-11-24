import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlToJson
{
    public static void ConvertToJson(String xmlPath, String jsonPath) {
        try
        {
            File xmlFile = new File(xmlPath);
            File jsonFile = new File(jsonPath);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);

            JSONObject jsonFilms = new JSONObject();
            JSONArray jsonFilmArray = new JSONArray();
            NodeList filmNodeList = doc.getElementsByTagName("film");

            for (int i = 0; i < filmNodeList.getLength(); i++)
            {
                Node filmNode = filmNodeList.item(i);
                if (filmNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element filmElement = (Element) filmNode;
                    String id = filmElement.getAttribute("id");
                    String filmCompany = filmElement.getAttribute("filmCompany");
                    String name = getElementValue(filmElement, "name");
                    String director = getElementValue(filmElement, "director");
                    int duration = Integer.parseInt(getElementValue(filmElement, "duration"));
                    int averageRating = Integer.parseInt(getElementValue(filmElement, "averageRating"));

                    // Создание объекта JSON для элемента <film>
                    JSONObject jsonFilm = new JSONObject();
                    jsonFilm.put("id", id);
                    jsonFilm.put("name", name);
                    jsonFilm.put("director", director);
                    jsonFilm.put("duration", duration);
                    jsonFilm.put("averageRating", averageRating);

                    // Добавление элемента <film> в соответствующий список фильмов компании
                    JSONObject jsonFilmCompany = getOrCreateFilmCompany(jsonFilms, filmCompany);
                    JSONArray filmArray = jsonFilmCompany.optJSONArray("films");
                    if (filmArray == null)
                    {
                        filmArray = new JSONArray();
                        jsonFilmCompany.put("films", filmArray);
                    }
                    filmArray.put(jsonFilm);
                }
            }

            try(FileWriter file = new FileWriter(jsonPath))
            {
                file.write(jsonFilms.toString(4));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getElementValue(Element parentElement, String tagName)
    {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        Element element = (Element) nodeList.item(0);
        return element.getTextContent();
    }

    private static JSONObject getOrCreateFilmCompany(JSONObject jsonFilms, String filmCompany)
    {
        JSONObject jsonFilmCompany = jsonFilms.optJSONObject(filmCompany);
        if (jsonFilmCompany == null) {
            jsonFilmCompany = new JSONObject();
            jsonFilms.put(filmCompany, jsonFilmCompany);
        }
        return jsonFilmCompany;
    }
}
