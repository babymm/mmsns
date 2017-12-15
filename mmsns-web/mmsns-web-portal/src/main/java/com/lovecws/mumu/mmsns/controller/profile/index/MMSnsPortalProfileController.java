package com.lovecws.mumu.mmsns.controller.profile.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 主页控制器
 * @date 2017-12-08 21:48
 */
@Controller
@RequestMapping("/profile")
public class MMSnsPortalProfileController {

    @RequestMapping(value = {"/{userName}/home", "/{userName}"}, method = RequestMethod.GET)
    public String home(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/home";
    }

    @RequestMapping(value = "/{userName}/home/vote", method = RequestMethod.GET)
    public String vote(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/vote";
    }

    @RequestMapping(value = "/{userName}/home/fans", method = RequestMethod.GET)
    public String fans(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/fans";
    }

    @RequestMapping(value = "/{userName}/home/fellow", method = RequestMethod.GET)
    public String fellow(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/fellow";
    }

    @RequestMapping(value = "/{userName}/home/access", method = RequestMethod.GET)
    public String access(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/access";
    }

    @RequestMapping(value = "/{userName}/home/score", method = RequestMethod.GET)
    public String score(@PathVariable String userName, HttpServletRequest request) {
        return "/profile/score";
    }

    @ResponseBody
    @RequestMapping(value = "/{userName}/home/score/data", method = RequestMethod.GET)
    public Object scoreData(@PathVariable String userName,
                            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                            HttpServletRequest request) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = (page - 1) * limit; i < page * limit; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", i + 1);
            map.put("scoreChanage", new Random().nextInt(100));
            map.put("remark", "每天登陆奖励" + i);
            map.put("createDate", new Date().toLocaleString());
            data.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", data);
        resultMap.put("code", 0);
        resultMap.put("msg", "success");
        resultMap.put("count", 100);
        return resultMap;
    }
}
