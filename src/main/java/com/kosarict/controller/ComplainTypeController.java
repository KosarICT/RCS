package com.kosarict.controller;

import com.kosarict.dao.ComplainTypeDao;
import com.kosarict.entity.ComplaintType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Younes on 04/12/2016.
 */
@Controller
public class ComplainTypeController {

    @Autowired
    private ComplainTypeDao complainTypeDao;



    @RequestMapping(value = "/complaintType", method = RequestMethod.GET)
    public ModelAndView complaintType() {
        ModelAndView model = new ModelAndView("complaintType");
        model.addObject("lists", getComplainTypeList());
        return model;
    }

    @RequestMapping(value = "/complaintType/api/saveComplainType", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveComplaintType(@RequestBody String model) {
        try {
            JSONArray jsonArray = new JSONArray(model);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            short complaintTypeId = (short) jsonObject.getInt("complaintTypeId");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");

            ComplaintType complaintType;

            if (complaintTypeId <= 0){
                complaintType = new ComplaintType();
            }else{
                complaintType = complainTypeDao.findComplaintTypeById(complaintTypeId);
            }

            complaintType.setTitle(title);
            complaintType.setDescription(description);
            complaintType.setEnable(true);

            short newComplainTypeId = complainTypeDao.saveComplaintType(complaintType);

            return String.valueOf(true);

        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/complaintType/api/getComplainTypeForEdit", method = RequestMethod.POST)
    public
    @ResponseBody
    String getComplaintForEdit(@RequestBody String complaintTypeId) {
        try {
            short id = Short.parseShort(complaintTypeId);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            ComplaintType complaintType = complainTypeDao.findComplaintTypeById(id);

            jsonObject.put("complaintTypeId",complaintType.getComplaintTypeId());
            jsonObject.put("title",complaintType.getTitle());
            jsonObject.put("description",complaintType.getDescription());

            jsonArray.put(jsonObject);

            return jsonArray.toString();
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    @RequestMapping(value = "/complaintType/api/deleteComplainType", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteComplaintType(@RequestBody String complaintTypeId) {
        try {
            short id = Short.parseShort(complaintTypeId);
            complainTypeDao.deleteComplaintType(id);
            return String.valueOf(true);
        } catch (Exception ex) {
            return String.valueOf(false);
        }
    }

    private List<ComplaintType> getComplainTypeList() {
        return complainTypeDao.getAllComplaintTypeList();
    }

}
