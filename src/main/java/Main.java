public class Main
{
    public static void main(String[] args) throws Exception {
        String jsonPath = "exampleFile.json";
        JSONtoXML.ConvertToXML(jsonPath, "XML_convertedFile.xml");

        String xmlPath = "exampleFile.xml";
        XmlToJson.ConvertToJson(xmlPath, "JSON_convertedFile.json");
    }
}
