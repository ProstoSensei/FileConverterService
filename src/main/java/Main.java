import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //String jsonPath = "firstFile.json";
        String xmlPath = "secondFile.xml";

        XMLtoJSON.ConvertToJSON(xmlPath, "convertedFile.json");
        //JSONtoXML.ConvertToXML(jsonPath, "");
    }
}
