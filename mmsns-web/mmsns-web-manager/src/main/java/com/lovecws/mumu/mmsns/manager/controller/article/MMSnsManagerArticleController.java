package com.lovecws.mumu.mmsns.manager.controller.article;

import com.lovecws.mumu.core.utils.FilterUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 文章分类控制器
 * @date 2017-12-11 13:45:
 */
@Controller
@RequestMapping("/manager/article/classify")
public class MMSnsManagerArticleController {

    @RequestMapping(method = RequestMethod.GET)
    public String articleClassify() {
        return "/manager/article/articleClassify";
    }

    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public Object articleClassifyData(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = (page - 1) * limit; i < page * limit; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", i);
            map.put("username", "lovecws" + i);
            map.put("sex", "sex" + i);
            map.put("city", "city" + i);
            map.put("sign", "sign" + i);
            map.put("experience", "123" + i);
            map.put("classify", "mumu");
            map.put("wealth", i);
            map.put("score", i);
            data.add(map);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", data);
        resultMap.put("code", 0);
        resultMap.put("msg", "success");
        resultMap.put("count", 100);
        System.out.println(resultMap);
        return resultMap;
    }
}
