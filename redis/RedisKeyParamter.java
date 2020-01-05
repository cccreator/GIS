package com.hisense.base.common.util.graphics;

/**
 * @Discription:Redis缓存key值统一管理
 * @Author: pxc
 * @Date:2019/11/22 22:50
 * @Version:1.0
 **/
public class RedisKeyParamter {

    //线路空间数据缓存key值
    public static final String cacheMapRouteGeoData = "routeGeo";

    //站点数据缓存key值，stationId为key
    public static final String cacheMapStationByStationID = "staByStaId";

    //站点数据缓存key值，stationNo为key
    public static final String cacheMapStationByStationNO = "staByStaNo";

    //线路数据缓存key值
    public static final String cacheMapRouteByRouteID = "routeByRouteId";

    //线路数据缓存key值
    public static final String cacheMapRouteByRouteCode = "routeByRouteCode";

    //子线数据缓存key值，包含线路数据
    public static final String cacheMapSubRouteByRouteID = "subRouteByRouteId";

    //子线数据缓存key值，根据子线id获取子线名称
    public static final String cacheMapRouteNameBySubRouteID = "rouNameBySubId";

    //公交专用道数据缓存key值
    public static final String cacheMapBusSpecialRouteGeoData = "busSpeRouteGeo";

    //单程子线关系数据缓存key值
    public static final String cacheMapSubRouteSegmentBySubRouteID = "subRouSegBySubId";

    //单程线路关系数据缓存key值
    public static final String cacheMapRouteSegmentByRouteID = "rouSegByRouteId";

    //单程线路关系数据缓存key值
    public static final String cacheMapSegmentInfoByRouteName = "segInfoByRouName";

    //车辆信息缓存key
    public static final String cacheMapBusInfoByProductId = "busInfoByProdId";

    //车辆信息缓存key
    public static final String cacheMapBusInfoByBusId = "busInfoByBusId";

    //车辆信息缓存key
    public static final String cacheMapBusInfoByBusCardNo = "busByCarNo";

    //充电站信息缓存key
    public static final String cacheMapNeStationInfoByStationId = "neStaByStaId";

    //充电站信息缓存key
    public static final String cacheMapNeStationInfoByStationNo = "neStaBySta";

    //充电站信息缓存key
    public static final String cacheMapSNetationInfoBySiteCode = "neStaBySiteCode";

    //场站信息缓存key
    public static final String cacheMapSiteInfoBySiteId = "siteBySiteId";

    //查询线路图标配置信息缓存key
    public static final String cacheMapRouteIcon = "routeIcon";

    //线路站点数据(线路ID,双程号，站点ID)信息缓存key
    public static final String cacheMapRouteStationByRouteId = "rouStaByRouId";

    //单程站点信息缓存key
    public static final String cacheMapSegmentStationBySegmentId = "segStaBySegId";

    //监控报警区域信息缓存key
    public static final String lstAlarmArea = "lAlarmArea";

    //监控报警区域信息缓存key
    public static final String cacheListMapStationtoStation = "statoSta";

    /************************************业务缓存*********************************************/

    //违规结存
    public static final String wgHighDistribution = "wgHighDistribution";

    //违章结存
    public static final String wzHighDistribution = "wzHighDis";

    //事故结存
    public static final String accHighDistribution = "accHighDis";

    //充电车辆数折线图
    public static final String ChargeBusNum = "charBusNum";

    //充电桩利用率折线图
    public static final String equipmentUseRate = "equUseRate";

    //小时充电量折线图
    public static final String hoursCapacity = "hoursCap";


}
