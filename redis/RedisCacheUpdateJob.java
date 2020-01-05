package com.hisense.smartroad.jms.job;

import com.hisense.base.common.config.PropertiesUtil;
import com.hisense.base.common.util.RedisUtil;
import com.hisense.base.common.util.graphics.RedisKeyParamter;
import com.hisense.hiose.cache.BusRealDataCacheService;
import com.hisense.hiose.service.ComprehensiveInfoHomePageNewEnergySZService;
import com.hisense.hiose.service.GISRouteAnalyseService;
import com.hisense.hiose.service.RedisCacheService;
import com.vividsolutions.jts.io.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author pxc
 * @date  19-11-18.
 */
@Component
public class RedisCacheUpdateJob {

    private Logger logger = LoggerFactory.getLogger(XtSynchronizeJob.class);
    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    GISRouteAnalyseService gisRouteAnalyseService;

    @Autowired
    private ComprehensiveInfoHomePageNewEnergySZService comprehensiveInfoHomePageNewEnergySZService;

    /**
     * 基础数据缓存
     * @throws ParseException
     */
    public void redisCacheUpdate() throws ParseException {


        String hostIp = this.getHostIp();
        if (/*hostIp.equals(propertiesUtil.getSystemJobIp())*/1==1 && propertiesUtil.getCityName().equals("SZ")){
            /**
             * 线路空间数据缓存
             */
            redisCacheService.routeGeoCache();


            /**
             * @return List<BasicInfoEntity>
             * @description 查询站点表信息。
             */
            redisCacheService.getStationInfo();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询线路表信息。
             */
            redisCacheService.getRouteAllInfo();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询子线路表信息。
             */
            redisCacheService.getSubRouteAllInfo();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询子线路表信息。
             */
            redisCacheService.getRouteNameBySubRouteId();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询公交专用道信息。
             */
            redisCacheService.getMapBusSpecialRouteGeoData();

            /**
             * @return List<BasicInfoEntity>
             * @description 线路、子线单程数据
             */
            redisCacheService.getcacheMapSubRouteSegmentBySubRouteID();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询子线路单程关系表信息。
             */
            redisCacheService.getMcsgeMentinfogs();

            /**
             * @return List<BasicInfoEntity>
             * @description 车辆信息缓存。
             */
            redisCacheService.getMcsgeBusInfogs();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询充电站表信息。
             */
            redisCacheService.getNeStationInfo();

            /**
             * @return List<BasicInfoEntity>
             * @description 场站表信息。
             */
            redisCacheService.getSiteInfo();

            /**
             * @return void
             * @description 查询线路图标配置Map。
             */
            redisCacheService.getRouteIcons();

            /**
             * 线路站点数据(线路ID,双程号，站点ID)
             */
            redisCacheService.getMcsgeRouteStationInfogs();

            /**
             * @return List<BasicInfoEntity>
             * @description 查询单程线路站点信息表表信息。
             */
            redisCacheService.getSegmentStation();

            /**
             * @return List<BasicInfoEntity>
             * @description 监控报警区域。
             */
           // redisCacheService.getAlarmArea();

            /**
             * @return List<BasicInfoEntity>
             * @description 监控报警区域。
             */
            redisCacheService.getMapStationToStation();

        }
    }

    /**
     * 清除can数据和gps当天累计缓存
     */
    public void clearCurDayCanSum(){
        /*RedisUtil.del(RedisKeyParamter.curDayCanSum);
        RedisUtil.del(RedisKeyParamter.curDayGpsSum);*/
        BusRealDataCacheService.canSumDataCache.clear();
        BusRealDataCacheService.canDataCache.clear();
    }

    //事故、违章高发
    public void highDistriAccount(){
        List list = gisRouteAnalyseService.calWgHighDisPointDetail("2019-01,2019-11");
        RedisUtil.setList(RedisKeyParamter.wgHighDistribution, list);
    }

    /**
     * 动态监控 -充电量 充电桩利用率 充电车辆数
     * 每隔半个小时更新一下
     */
    public void getChargingService() {

        try {
            //充电车辆数
            List<Map<String, String>> busMap = comprehensiveInfoHomePageNewEnergySZService.getChargeBusNum(null);
            RedisUtil.setList(RedisKeyParamter.ChargeBusNum, busMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //充电桩利用率
            List<Map<String, String>> equipmentUseRateList = comprehensiveInfoHomePageNewEnergySZService.getEquipmentUseRate();
            RedisUtil.setList(RedisKeyParamter.equipmentUseRate, equipmentUseRateList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //小时充电量
            List<Map<String, String>> hoursCapacityList = comprehensiveInfoHomePageNewEnergySZService.getHoursCapacity(null);
            RedisUtil.setList(RedisKeyParamter.hoursCapacity, hoursCapacityList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    /**
     * 获取当前服务器地址
     * @return
     */
    public String getHostIp() {
        String hostIp = "";
        try {
            //用 getLocalHost() 方法创建的InetAddress的对象
            InetAddress address = InetAddress.getLocalHost();
            hostIp = address.getHostAddress();//获取IP地址
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

}
