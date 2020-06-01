package robtest.core.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public abstract class AbstractApi {
    private String host;
    private int port;
    private String user;
    private String password;

    public AbstractApi(String host) {
        this(host, 80);
    }

    public AbstractApi(String host, int port) {
        this(host, port, null, null);
    }

    public AbstractApi(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    protected String request(String path, String method, String body, Map<String, String> requestProperties) {
        String result = null;
        try {
            URL url = new URL(String.format("http://%s:%d/api%s", this.host, this.port, path));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (!StringUtils.isEmpty(this.user) && !StringUtils.isEmpty(this.password)) {
                String userCredentials = String.format("%s:%s", this.user, this.password);
                con.setRequestProperty("Authorization", String.format("Basic %s", new String(Base64.getEncoder().encode(userCredentials.getBytes()))));
            }
            if (requestProperties != null && requestProperties.keySet().size() > 0) {
                for (var key : requestProperties.keySet()) {
                    con.setRequestProperty(key, requestProperties.get(key));
                }
            }
            con.setRequestMethod(method);
            if (!StringUtils.isEmpty(body)) {
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(body);
                out.flush();
                out.close();
            }
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            System.out.println(status);
            if (status >= 200 && status <= 300) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer content = new StringBuffer();
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                result = content.toString();
            } else {
                System.out.println(con.getResponseMessage());
                throw new Exception(con.getResponseMessage());
            }
            con.disconnect();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    protected Object request(String path, String method, String body, Map<String, String> requestProperties, Class clazz) {
        return request(path, method, body, requestProperties, clazz, false);
    }

    protected Object request(String path, String method, String body, Map<String, String> requestProperties, Class clazz, boolean isList) {
        String content = request(path, method, body, requestProperties);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Object instance = null;
        if (clazz != null) {
            try {
                if (isList) {
                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                    var collectionType = typeFactory.constructCollectionType(List.class, clazz);
                    instance = objectMapper.readValue(content, collectionType);
                } else {
                    instance = objectMapper.readValue(content, clazz);
                }

            } catch (JsonProcessingException exc) {
                exc.printStackTrace();
            }
        }
        return instance;
    }

    protected Object request(String path, String method, Object body, Map<String, String> requestProperties, Class clazz) {
        return request(path, method, body, requestProperties, clazz, false);
    }

    protected Object request(String path, String method, Object body, Map<String, String> requestProperties, Class clazz, boolean isList) {
        try {
            if (requestProperties == null)
                requestProperties = new HashMap<>();
            requestProperties.put("Content-Type", "application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            var bodyString = objectMapper.writeValueAsString(body);
            System.out.println(bodyString);
            return request(path, method, bodyString, requestProperties, clazz, isList);
        } catch (JsonProcessingException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    protected <T> List<T> list(Object l, Class<T> classType) {
        if (l != null && l instanceof List) {
            return new ArrayList<T>((List) l);
        }
        return null;
    }
}
