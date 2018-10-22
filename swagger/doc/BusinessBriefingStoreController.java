package com.plateno.o2omember.controller;

import com.google.gson.Gson;
import com.plateno.o2omember.common.constant.IndexTypeEnum;
import com.plateno.o2omember.common.constant.ResponseHeader;
import com.plateno.o2omember.dto.store.*;
import com.plateno.o2omember.pojo.service.store.UserStoreKeySettingInfo;
import com.plateno.o2omember.service.store.BusinessBriefingByAllStoreV2Service;
import com.plateno.o2omember.service.store.ManagementReportByAllStoreV2Service;
import com.plateno.o2omember.view.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @Title: BusinessBriefingController
 * @Description: 【分店经营简报】Controller
 * @Author: Evan
 * @Date: 2017年12月5日下午2:05:54
 */
@Controller
@Api(value = "/businessBriefing", tags = "【经营简报】")
@Slf4j
public class BusinessBriefingStoreController extends DataController {

    @Resource
    private BusinessBriefingByAllStoreV2Service businessBriefingByAllStoreV2Service;

    @Resource
    private ManagementReportByAllStoreV2Service managementReportByAllStoreV2Service;

    /**
     * 根据【日期】、【分店id(铂涛、锦江)】查询【分店铂涛、锦江)各渠道营收占比】信息
     */
    @ApiOperation(value = "分店(铂涛、锦江)各渠道营收占比", notes = "根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道营收占比】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、总营收|chkinRoomTotal"
            + "<br>2、渠道营收列表|channelAnalysisDtoList"
            + "<br>|---2.1、渠道|rootPlatformGroup"
            + "<br>|---2.2、营收|ttvActual"
            + "<br>|---2.3、占比|rate"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/revenueChannelRatioByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean revenueChannelRatioByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)接收到的参数："
                + "tk = {},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            ChannelAnalysis channelAnalysis = businessBriefingByAllStoreV2Service.revenueChannelRatioByStoreV2(chainid, startDate, endDate);
            if (channelAnalysis != null) {
                view.setData(channelAnalysis);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)营收渠道占比】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(channelAnalysis);
                log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【营收渠道占比】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】查询【分店营收渠道占比】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)营收渠道占比】信息(/businessBriefingByAllStore/revenueChannelRatioByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 根据【日期】、【分店id(锦江、铂涛)】查询--【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息
     */
    @ApiOperation(value = "分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)", notes = "根据【日期】、【分店id】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、礼包id|giftId"
            + "<br>2、礼包名称|giftType"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/giftTypeByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean giftTypeByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)接收到的参数："
                + "tk = {},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            List<GiftsTopDto> giftsTopDtoList = businessBriefingByAllStoreV2Service.giftTypeByStoreV2(chainid, startDate, endDate);
            if (giftsTopDtoList != null) {
                view.setData(giftsTopDtoList);
                view.setMsg("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(giftsTopDtoList);
                log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【销售礼包金额走势】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包金额走势(销售礼包名称)】信息(/businessBriefingByAllStore/giftTypeByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }


    /**
     * 根据【日期】、【分店id】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息
     */
    @ApiOperation(value = "分店(锦江、铂涛)销售礼包金额走势", notes = "根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/giftsSaleAnalysisByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean giftsSaleAnalysisByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "giftId", required = true, value = "礼包id") @RequestParam("giftId") String giftId,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)接收到的参数："
                + "tk = {},chainid={},giftId={}，startDate={},endDate={}", tk, chainid, giftId, startDate, endDate);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck2(tk,chainid,startDate,endDate,giftId,"礼包id");
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            ManagementReportDto managementReportDto = businessBriefingByAllStoreV2Service.giftsSaleAnalysisByStoreV2(chainid, giftId, startDate, endDate);
            if (managementReportDto != null) {
                managementReportDto.setDateShow(startDate + "~" + endDate);
                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【销售礼包金额走势】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(锦江、铂涛)】、【礼包id】查询【分店(锦江、铂涛)销售礼包金额走势】信息(/businessBriefing/giftsSaleAnalysisByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }


    /**
     * 根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比】信息
     */
    @ApiOperation(value = "分店(锦江、铂涛)销售礼包类型数量占比 ", notes = "根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、销售总额|saleGiftCntTotal"
            + "<br>2、礼包列表|GiftsSaleDistributionDtoList"
            + "<br>|---2.1、礼包名称|giftType"
            + "<br>|---2.2、礼包数量|saleGiftCnt"
            + "<br>|---2.3、礼包数量比率|saleGiftCntRate"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/giftsSaleDistributionByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean giftsSaleDistributionByStore(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)接收到的参数："
                + "tk = {},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

//      GiftsSaleDistributionAllDto giftsSaleDistributionAllDto = businessBriefingByAllStoreV2Service.giftsSaleDistributionByStoreV2(chainid, startDate, endDate);
            GiftsSaleDistributionAllDto giftsSaleDistributionAllDto = businessBriefingByAllStoreV2Service.giftsSaleDistributionByStoreTop5(chainid, startDate, endDate);
            if (giftsSaleDistributionAllDto != null) {
                view.setData(giftsSaleDistributionAllDto);
                view.setMsg("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(giftsSaleDistributionAllDto);
                log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【销售礼包类型数量占比 】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(锦江、铂涛)】查询【分店(锦江、铂涛)销售礼包类型数量占比 】信息(/businessBriefing/giftsSaleDistributionByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息
     */
    @ApiOperation(value = "分店(铂涛、锦江)分销渠道开房数占比", notes = "根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、总开房数|chkinRoomTotal"
            + "<br>2、渠道开房列表|channelAnalysisDtoList"
            + "<br>|---2.1、渠道|rootPlatformGroup"
            + "<br>|---2.2、开房数|ttvActual"
            + "<br>|---2.3、占比|rate"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/otaChannelRatioByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean otaChannelRatioByStore(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)接收到的参数："
                + "tk = {},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            ChannelAnalysis channelAnalysis = businessBriefingByAllStoreV2Service.otaChannelRatioByStoreV2(chainid, startDate, endDate);
            if (channelAnalysis != null) {
                view.setData(channelAnalysis);
                view.setMsg("根据【日期】、【分店id】查询【分店OTA分销渠道占比分布】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(channelAnalysis);
                log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【分销渠道开房数占比】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】查询【分店OTA分销渠道占比分布】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)分销渠道开房数占比】信息(/businessBriefingByAllStore/otaChannelRatioByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比分布】信息
     */
    @ApiOperation(value = "分店(铂涛、锦江)各渠道开房数占比", notes = "根据【日期】、【分店id】查询【分店(铂涛、锦江)各渠道开房数占比】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、总开房数|chkinRoomTotal"
            + "<br>2、渠道开房列表|channelAnalysisDtoList"
            + "<br>|---2.1、渠道|rootPlatformGroup"
            + "<br>|---2.2、开房数|ttvActual"
            + "<br>|---2.3、占比|rate"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/chkinRoomRatioByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean chkinRoomRatioByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id】查询【分店开房数占比分布】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)接收到的参数："
                + "tk = {},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            ChannelAnalysis channelAnalysis = businessBriefingByAllStoreV2Service.chkinRoomRatioByStoreV2(chainid, startDate, endDate);
            if (channelAnalysis != null) {
                view.setData(channelAnalysis);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(channelAnalysis);
                log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("【开房数占比分布】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】查询【分店开房数占比分布】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江)】查询【分店(铂涛、锦江)各渠道开房数占比】信息(/businessBriefingByAllStore/chkinRoomRatioByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息
     */
    @ApiOperation(value = " 经营概要(铂涛、锦江分店)", notes = "根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、好评率|cmPraiseRate"
            + "<br>2、信息完整度|cInfoFull"
            + "<br>3、可售房|onsalesRooms"
            + "<br>4、总房数|roomsCnt"
            + "<br>5、开房率|occ"
            + "<br>6、开房数|chkinRoom"
            + "<br>7、全日租房|fullDateChkin"
            + "<br>8、时租房|hourChkin"
            + "<br>9、过夜开房数|overnightChkin"
            + "<br>10、过夜开房率|overnightChkinRatio"
            + "<br>11、Revpar|revpar"
            + "<br>12、ADR|adr"
            + "<br>13、注册会员数|members"
            + "<br>14、实收房费|ttvAfterDiscount"
            + "<br>15、分店类型|businessType"
            + "<br>16、没有数据的提示|noDataPrompt"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/businessSummaryByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean businessSummaryByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)接收到的参数：tk = {},chainid={},startDate={},endDate={}",
                tk, chainid, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck(tk,chainid,startDate,endDate);
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            BusinessSummaryDto businessSummaryDto = businessBriefingByAllStoreV2Service.businessSummaryByStoreV2(chainid, startDate, endDate);

            /**
             * 如果数据为空，弹出如下的提示
             */
            if(StringUtils.isNotBlank(businessSummaryDto.getNoDataPrompt())){
                view.setMsg(businessSummaryDto.getNoDataPrompt());
                view.setStatus(ResponseHeader.STATUS_500);

            }else{
                if (businessSummaryDto != null) {
                    view.setData(businessSummaryDto);
                    view.setMsg("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息成功");

                    Gson gson = new Gson();
                    String returnJson = gson.toJson(businessSummaryDto);
                    log.info("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)返回的数据为：{}", returnJson);
                } else {
                    view.setMsg("【经营概要】信息为空");
                    view.setStatus(ResponseHeader.STATUS_100);
                }
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)出现异常。 tk={},chainid={},startDate={},endDate={}",
                    tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】查询【分店经营概要】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id】查询【分店(铂涛、锦江)经营概要】信息(/businessBriefingByAllStore/businessSummaryByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }


    /**
     * 获取【经营简报】之【前台扫码报表名称列表】
     *
     * @param tk
     * @return
     */
    @ApiOperation(value = "(新)前台扫码报表名称列表", notes = "获取【经营简报】之【前台扫码报表名称列表】"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、礼包id|giftId"
            + "<br>2、礼包名称|giftType"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/scanCodeNameList/v1", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean scanCodeNameList(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk) {

        Long long1 = System.currentTimeMillis();
        log.info("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)于{}开始，", long1);
        log.info("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)接收到的参数：tk = {}", tk);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录
             */
            Map<String,Object> userCheckMap = userIsLogin(tk,view);
            if(null != userCheckMap.get("view")){
                return (ResultBean)userCheckMap.get("view");
            }

            //获取【前台扫码报表名称列表】
            List<ScanCodeNameDto> scanCodeNameDtoList = businessBriefingByAllStoreV2Service.getScanCodeNameList();

            //获取成功
            view.setData(scanCodeNameDtoList);
            view.setMsg("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)数据成功");

            Gson gson = new Gson();
            String returnJson = gson.toJson(scanCodeNameDtoList);
            log.info("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)返回的数据为：{}", returnJson);


        } catch (Exception e) {
            log.error("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)出现异常。 tk={},e={}", tk, e);

            view.setMsg("获取【前台扫码报表名称列表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)于{}结束。", long2);
        log.info("获取【前台扫码报表名称列表】信息(/businessBriefing/scanCodeNameList/v1)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】信息
     *
     * @param tk
     * @param chainid:分店id
     * @param startDate:开始时间
     * @param endDate:结束时间
     * @return
     */
    @ApiOperation(value = "(新)前台扫码报表(分店)", notes = "根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            , httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/scanCodeAnalysisByStore/v1", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean scanCodeAnalysisByStore(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "scanCodeId", required = true, value = "扫码id") @RequestParam("scanCodeId") String scanCodeId,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd") @RequestParam("endDate") String endDate) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)于{}开始，", long1);
        log.info("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)接收到的参数：tk = {},chainid={},scanCodeId={}，startDate={},endDate={}",
                tk, chainid, scanCodeId, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验用户是否登录、参数校验
             */
            Map<String,String> reportCheckMap = reportCheck2(tk,chainid,startDate,endDate,scanCodeId,"扫码id");
            if(null != reportCheckMap){
                view.setMsg(reportCheckMap.get("error"));
                view.setStatus(ResponseHeader.STATUS_202);
                return view;
            }

            if (rexpDate(startDate) && rexpDate(endDate)) {
                startDate = startDate.replace(".", "-");
                endDate = endDate.replace(".", "-");
            }

            ManagementReportDto managementReportDto = businessBriefingByAllStoreV2Service.scanCodeAnalysisByStore(chainid, scanCodeId, startDate, endDate);
            if (managementReportDto != null) {
                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);
                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }
        } catch (Exception e) {
            log.error("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)出现异常。 tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)于{}结束。", long2);
        log.info("根据【日期】、【分店id】、【扫码id】查询【前台扫码报表】(分店)信息(/businessBriefingByAllStore/scanCodeAnalysisByStore/v1)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id】查询【扫码TTV图表】信息
     *
     * @param tk
     * @param chainid:分店id
     * @param startDate:开始时间
     * @param endDate:结束时间
     * @return
     */
    @ApiOperation(value = "(6) 扫码TTV图表【非前端对接的接口】", notes = "根据【日期】、【分店id】查询【扫码TTV图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefing/scanCodeTTVReport/v1", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean scanCodeTTVReport(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", defaultValue = "2017-01-01", value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", defaultValue = "2017-01-31", value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】") @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)于{}开始，", long1);
        log.info("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)接收到的参数：tk = {},chainid={},dateType={},startDate={},endDate={}dateData={}",
                tk, chainid, dateType, startDate, endDate, dateData);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            /*
             * 格式转换
             */
            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.scanCodeTTVReport(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(dateList.get(3));
                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id】查询【扫码TTV】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id】查询【扫码TTV】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)出现异常。 tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id】查询【扫码TTV】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)于{}结束。", long2);
        log.info("根据【日期】、【分店id】查询【扫码TTV】信息(/businessBriefing/scanCodeTTVReport/v1)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 铂涛、锦江【集团用户】、【分店用户】查询分店时，获取的【关键指标设置】
     * 不显示【巡查次数】指标
     */
    @ApiOperation(value = "(新)铂涛、锦江【集团用户】、【分店用户】查询分店时，获取的【关键指标设置】", notes = "铂涛、锦江【集团用户】、【分店用户】查询分店时，获取的【关键指标设置】"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、大类名称|firstKind"
            + "<br>2、分店关键指标列表|userStoreKeySettingList"
            + "<br>|---2.1、关键指标名称|keyName"
            + "<br>|---2.2、关键指标的值(显示:true;隐藏:false)|showFlag"
            , httpMethod = "GET")
    @RequestMapping(value = "/index/getStoreKeyIndicators/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean getStoreKeyIndicators(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk) {

        Long long1 = System.currentTimeMillis();
        log.info("获取分店关键指标(/index/getStoreKeyIndicators/v1)于{}开始，", long1);
        log.info("获取分店关键指标(/index/getStoreKeyIndicators/v1)接收到的参数：tk = {}", tk);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验用户是否登录
             */
            Map<String,Object> userCheckMap = userIsLogin(tk,view);
            if(null != userCheckMap.get("view")){
                return (ResultBean)userCheckMap.get("view");
            }

            List<UserStoreKeySettingInfo> userStoreKeySettingInfoList = managementReportByAllStoreV2Service.getDefaultKeySetting();

            if (userStoreKeySettingInfoList != null && !userStoreKeySettingInfoList.isEmpty()) {

                //获取成功
                view.setData(userStoreKeySettingInfoList);
                view.setMsg("获取分店关键指标(/index/getStoreKeyIndicators/v1)数据成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(userStoreKeySettingInfoList);
                log.info("获取分店关键指标(/index/getStoreKeyIndicators/v1)返回的数据为：{}", returnJson);
            }
        } catch (Exception e) {

            log.error("获取分店关键指标(/index/getStoreKeyIndicators/v1)出现异常。 tk = {},e = {}", tk, e);

            view.setMsg("获取统计范围出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }

        Long long2 = System.currentTimeMillis();

        log.info("获取分店关键指标(/index/getStoreKeyIndicators/v1)于{}结束。", long2);
        log.info("获取分店关键指标(/index/getStoreKeyIndicators/v1)用时{}毫秒。", (long2 - long1));

        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id】查询【营收图表】信息
     */
    @ApiOperation(value = "(0) 分店(铂涛、锦江分店)图表入口", notes = "根据【日期】、【分店id(铂涛、锦江分店)】、【关键指标id】查询【图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/reportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean report(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData,
            @ApiParam(name = "keyId", required = true, value = "关键指标id") @RequestParam("keyId") String keyId) {

        Long long1 = System.currentTimeMillis();
        log.info("分店(铂涛、锦江分店)图表入口(/businessBriefingByAllStore/reportByStore/v2)于{}开始，", long1);
        log.info("分店(铂涛、锦江分店)图表入口(/businessBriefingByAllStore/reportByStore/v2)接收到的参数：tk = {},chainid={},dateType={},startDate={},endDate={},keyId={}",
                tk, chainid, dateType, startDate, endDate, keyId);

        if (IndexTypeEnum.HOUSEREVENUE.getIndexId().equals(keyId)) {
            //【房费收入】
            return ttvAfterDiscountReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.GIFTTTV.getIndexId().equals(keyId)) {
            //【销售礼包金额】
            return giftTTVReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.BUSINESSTTV.getIndexId().equals(keyId)) {
            //【商旅渠道营收】
            return bussinessTTVReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.OPENROOMNUMBER.getIndexId().equals(keyId)) {
            //【开房数】
            return chkinRoomReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.OPENROOMNUMBERRATE.getIndexId().equals(keyId)) {
            //【开房率】
            return occReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.ONLINEOPENROOMNUMBER.getIndexId().equals(keyId)) {
            //【线上直销开房数】
            return onlineDirectSellingChkinRoomReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.PMSOPENROOMNUMBER.getIndexId().equals(keyId)) {
            //【线下直销开房数】
            return pmsChkinRoomReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.OTAOPENROOMNUMBER.getIndexId().equals(keyId)) {
            //【分销开房数】
            return otaChkinRoomReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.RECEPTIONSCANOPENROOMNUMBER.getIndexId().equals(keyId)) {
            //【扫码开房数】
            return underLineScanCodeChkinRoomReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.REVPAR.getIndexId().equals(keyId)) {
            //【RevPAR图表】
            return revparReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        } else if (IndexTypeEnum.ADR.getIndexId().equals(keyId)) {
            //【ADR图表】
            return adrReportByStoreV2(tk, chainid, dateType, startDate, endDate, dateData);
        }
        return null;
    }


    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息
     */
    @ApiOperation(value = "房费收入图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean ttvAfterDiscountReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】") @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.ttvAfterDiscountReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id】查询【房费收入图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【房费收入图表】信息(/businessBriefingByAllStore/ttvAfterDiscountReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV图表】信息
     */
    @ApiOperation(value = "销售礼包金额图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【扫码TTV图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/giftTTVReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean giftTTVReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】") @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {

            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.giftTTVReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v1)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v1)出现异常。 tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v1)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【礼包TTV】信息(/businessBriefingByAllStore/giftTTVReportByStore/v1)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV图表】信息
     */
    @ApiOperation(value = "商旅渠道营收图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/bussinessTTVReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean bussinessTTVReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.bussinessTTVReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【商旅TTV】信息(/businessBriefingByAllStore/bussinessTTVReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息
     */
    @ApiOperation(value = "开房数图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/chkinRoomReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean chkinRoomReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.chkinRoomReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房数图表】信息(/businessBriefingByAllStore/chkinRoomReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息
     */
    @ApiOperation(value = "开房率图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/occReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean occReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)接收到的参数：tk = {},chainid={},dateType={},startDate={},endDate={}",
                tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.occReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【开房率图表】信息(/businessBriefingByAllStore/occReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息
     */
    @ApiOperation(value = "线上直销开房数图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean onlineDirectSellingChkinRoomReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)接收到的参数："
                        + "tk = {},chainid={},dateType={},startDate={},endDate={}",
                tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.onlineDirectSellingChkinRoomReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【线上直销开房数图表】信息(/businessBriefingByAllStore/onlineDirectSellingChkinRoomReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }


    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息
     */
    @ApiOperation(value = "PMS开房数图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean pmsChkinRoomReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.pmsChkinRoomReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【PMS开房数图表】信息(/businessBriefingByAllStore/pmsChkinRoomReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息
     */
    @ApiOperation(value = "OTA分销开房数图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/otaChkinRoomReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean otaChkinRoomReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.otaChkinRoomReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【OTA分销开房数图表】信息(/businessBriefingByAllStore/otaChkinRoomReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息
     */
    @ApiOperation(value = "扫码开房数图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean underLineScanCodeChkinRoomReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.underLineScanCodeChkinRoomReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)出现异常。"
                    + " tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【前台扫码开房数图表】信息(/businessBriefingByAllStore/underLineScanCodeChkinRoomReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息
     */
    @ApiOperation(value = "RevPAR图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/revparReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean revparReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.revparReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)出现异常。 "
                    + "tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【Revpar图表】信息(/businessBriefingByAllStore/revparReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

    /**
     * 【经营概要】
     * 根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息
     */
    @ApiOperation(value = "ADR图表(分店(铂涛、锦江分店))【非前端对接的接口】", notes = "根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息"
            + "<br>========【返回数据的说明】========<br> 【字段描述】|【 字段名】"
            + "<br>1、数据总值|yAxisTotal"
            + "<br>2、统计的时间区间|dateShow"
            + "<br>3、图表数据列表|managementReportVoList"
            + "<br>|---3.1、x轴数据|xAxis"
            + "<br>|---3.2、y轴数据|yAxis"
            ,
            httpMethod = "GET")
    @RequestMapping(value = "/businessBriefingByAllStore/adrReportByStore/v2", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean adrReportByStoreV2(
            @ApiParam(name = "tk", required = true, value = "用户token") @RequestParam("tk") String tk,
            @ApiParam(name = "chainid", required = true, value = "门店id") @RequestParam("chainid") String chainid,
            @ApiParam(name = "dateType", defaultValue = "5", value = "日期类型(日:1;周:2;月:3;季度:4;自定义时间:5)") @RequestParam("dateType") String dateType,
            @ApiParam(name = "startDate", required = true, value = "开始时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("startDate") String startDate,
            @ApiParam(name = "endDate", required = true, value = "结束时间,格式yyyy-MM-dd或yyyy.MM.dd(当【日期类型】为【日】、【自定义】时)") @RequestParam("endDate") String endDate,
            @ApiParam(name = "dateData", defaultValue = "2017-01", value = "日期类型为【周】，格式为【yyyy-ww】;日期类型为【月】，格式为【yyyy-MM】;日期类型为【季度】，格式为【yyyy-q】")
            @RequestParam("dateData") String dateData) {

        Long long1 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)于{}开始，", long1);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)接收到的参数："
                + "tk = {},chainid={},dateType={},startDate={},endDate={}", tk, chainid, dateType, startDate, endDate);

        ResultBean view = new ResultBean();

        try {
            /**
             * 校验
             */
            ResultBean checkView = revenueReportCheck(view,tk,chainid,dateType,startDate,endDate,dateData);
            if (null != checkView) {
                return checkView;
            }

            List<String> dateList = getDateStr(dateType, startDate, endDate, dateData);

            ManagementReportDto managementReportDto = managementReportByAllStoreV2Service.adrReportByStore(chainid, dateList.get(0), dateList.get(1));
            if (managementReportDto != null) {

                //显示的统计时间区间
                managementReportDto.setDateShow(startDate + "~" + endDate);

                view.setData(managementReportDto);
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息成功");

                Gson gson = new Gson();
                String returnJson = gson.toJson(managementReportDto);
                log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)返回的数据为：{}", returnJson);
            } else {
                view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息为空");
                view.setStatus(ResponseHeader.STATUS_100);
            }

        } catch (Exception e) {
            log.error("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)出现异常。"
                    + " tk={},chainid={},startDate={},endDate={}", tk, chainid, startDate, endDate, e);

            view.setMsg("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息出现异常");
            view.setStatus(ResponseHeader.STATUS_500);
        }
        Long long2 = System.currentTimeMillis();
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)于{}结束。", long2);
        log.info("根据【日期】、【分店id(铂涛、锦江分店)】查询【ADR图表】信息(/businessBriefingByAllStore/adrReportByStore/v2)用时{}毫秒。", (long2 - long1));
        return view;
    }

}
