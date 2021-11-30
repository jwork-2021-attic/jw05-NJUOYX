package nju.java.logic.system.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import nju.java.logic.element.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



public class ElementFactory {
    public static Element getElement(String name){
        InputStream inputStream = null;
        try {
            inputStream = getStream(name);
        }catch (IOException e){
            e.printStackTrace();
        }

        ObjectMapper objectMapper = Initial.getINSTANCE().getObjectMapper();
        try {
            return (Element)objectMapper.readValue(inputStream, Class.forName(name));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static InputStream getStream(String name) throws IOException {
        String path = "element/" + name +".json";
        URL url = ElementFactory.class.getClassLoader().getResource(path);
        assert(url != null);
        return url.openStream();
    }

}
