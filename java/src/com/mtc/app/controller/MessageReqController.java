/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBBizMessageService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.RequestWeather;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBBizMessage;
import org.apache.axis.Message;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: MessageReqController
 * @Description:
 * @Date 2017-02-14 12:07
 * @Author seymour zhang
 * 
 */
@Component
@RequestMapping("/sys/message")
public class MessageReqController {

	private static Logger mLogger = Logger.getLogger(MessageReqController.class);

	@Autowired
	private BaseQueryService mBaseQueryService;

	@Autowired
	private SBBizMessageService sbBizMessageService;

	@RequestMapping("/queryList")
	public void queryList(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing MessageReqController.queryList");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject params = jsonobject.optJSONObject("params");
			int UserId = params.optInt("UserId");
			String sql = "select id,message_title,message_desc,status,date_format(create_time, '%Y-%m-%d %H:%i') update_time from s_b_biz_message where user_id = " + UserId + " order by create_time desc";
			List<HashMap<String, Object>> lcp = mBaseQueryService.selectMapByAny(sql);
			JSONArray MessageArray = new JSONArray();
			for (HashMap<String, Object> stringObjectHashMap : lcp) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("MessageId", stringObjectHashMap.get("id"));
				jsonObject.put("MessageTitle", stringObjectHashMap.get("message_title"));
				jsonObject.put("MessageDetail", stringObjectHashMap.get("message_desc"));
				jsonObject.put("status", stringObjectHashMap.get("status"));
				jsonObject.put("MessageTime", stringObjectHashMap.get("update_time"));
				MessageArray.put(jsonObject);
			}
			resJson.put("Result", "Success");
			resJson.put("Error", "");
			resJson.put("MessageArray", MessageArray);
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			dealRes = Constants.RESULT_FAIL;
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Result", "程序处理错误，请联系管理员！");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing MessageReqController.queryList");
	}

	@RequestMapping("/markRead")
	public void markRead(HttpServletRequest request, HttpServletResponse response) {
		mLogger.info("=======Now start executing MessageReqController.markRead");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			int UserId = jsonobject.optInt("id_spa");
			JSONObject params = jsonobject.optJSONObject("params");
			String MarkType = params.optString("MarkType");
			String MarkResult = params.optString("MarkResult");
			int MessageId = params.optInt("MessageId");
			if (MarkType.equals("Single")){
				SBBizMessage record = sbBizMessageService.selectByPrimaryKey(MessageId);
				record.setStatus("02");
				record.setUpdateTime(new Date());
				sbBizMessageService.update(record);
			} else if (MarkType.equals("All")){
				String sql = "update s_b_biz_message set status = '02',update_time = now() where user_id = " + UserId + " and status = '01'";
				int result = mBaseQueryService.updateIntergerByAny(sql);
  			}
  			resJson.put("Result", "Success");
			resJson.put("Error", "");
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			dealRes = Constants.RESULT_FAIL;
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", "程序处理错误，请联系管理员！");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing MessageReqController.markRead");
	}
}
