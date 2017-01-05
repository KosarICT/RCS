package com.kosarict.controller;

import com.kosarict.dao.TabDao;
import com.kosarict.dao.UserDao;
import com.kosarict.entity.Tab;
import com.kosarict.entity.Users;
import com.kosarict.model.Constant;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sadegh-Pc on 11/30/2016.
 */
@Controller
public class WSMobile {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TabDao tabDao;


    @RequestMapping(value = "/ws/api/checkUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String loginUser(@RequestBody String model) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL(Constant.CHECK_USER_URL + model);

            Map<String, List<String>> parameter = splitQuery(url);

            String userName = URLDecoder.decode(parameter.get("User").get(0), "UTF-8");
            String password = URLDecoder.decode(parameter.get("Password").get(0), "UTF-8");

            List<Users> usersList = userDao.checkUser(userName, password);

            List<Tab> tabList = tabDao.getAllMobileTabList();

            if (usersList.size() > 0) {
                Users currentUser = usersList.get(0);

                if (currentUser.getLocked() != 0) {
                    jsonObject.put("status", "disable");
                    jsonObject.put("description", "disable");
                } else {
                    jsonObject.put("status", "ok");
                    jsonObject.put("description", "ok");
                    jsonObject.put("tabList", tabList);
                }

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            } else {
                jsonObject.put("status", "404");
                jsonObject.put("description", "User not exists");

                jsonArray.put(jsonObject);

                return jsonArray.toString();
            }

        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }

    @RequestMapping(value = "/ws/api/refreshData", method = RequestMethod.POST)
    public
    @ResponseBody
    String refreshData() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            return jsonArray.toString();
        } catch (Exception ex) {
            jsonObject.put("status", "error");
            jsonObject.put("description", ex.toString());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        }
    }


    private Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();

        final String[] pairs = url.getQuery().split("&");

        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;

            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }

            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }

        return query_pairs;
    }
}
