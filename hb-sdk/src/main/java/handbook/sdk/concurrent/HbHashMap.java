package handbook.sdk.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HbHashMap {

    public static void main(String[] args){

        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");

    }
}
