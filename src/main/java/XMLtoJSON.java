import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLtoJSON
{
    public static void ConvertToJSON(String filePath, String outputPath) throws IOException
    {
        File xmlFile = new File(filePath);
        File jsonFile = new File(outputPath);
        ObjectMapper xmlMapper = new XmlMapper();
        Object xmlObject = xmlMapper.readValue(xmlFile, Object.class);
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        jsonMapper.writeValue(jsonFile, xmlObject);

        System.out.println(jsonMapper.writeValueAsString(xmlObject));
    }
}
