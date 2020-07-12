package com.fred.json;

import net.sf.json.JSONObject;

/**
 * @auther fred
 * @create 2020-05-27 8:42
 */

public class JsonTools {
    /**
     * @param key
     * @param value
     * @return Json对象的字符串表示
     */
    public static String createJsonString(String key, Object value){
        JSONObject jsonobject = new JSONObject();
        jsonobject.put(key, value);
        return jsonobject.toString();
    }
}
