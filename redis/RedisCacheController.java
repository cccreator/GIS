package com.hisense.smartroad.hiose.web.controller.BusMonitor;

import com.hisense.base.common.util.RedisUtil;
import com.hisense.base.common.util.graphics.RedisKeyParamter;
import com.hisense.base.common.web.BaseController;
import com.hisense.hiose.Gps.bean.dbCommon.ClsSegmentInfo;
import com.hisense.hiose.Gps.common.BusinessException;
import com.hisense.hiose.service.RedisCacheService;
import com.hisense.smartroad.TestWatcher;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @author pxc
 * @date 2019-11-24
 */
@Controller
@RequestMapping(value = "/BusMonitor/RedisCache")
public class RedisCacheController extends BaseController {

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 线路空间数据缓存
     */
    @RequestMapping(value = "routeGeoCache", method = RequestMethod.POST)
    @ResponseBody
    public void routeGeoCache(){
        redisCacheService.routeGeoCache();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询站点表信息。
     */
    @RequestMapping(value = "getStationInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getStationInfo(){
        redisCacheService.getStationInfo();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询线路表信息。
     */
    @RequestMapping(value = "getRouteAllInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getRouteAllInfo(){
        redisCacheService.getRouteAllInfo();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询子线路表信息。
     */
    @RequestMapping(value = "getSubRouteAllInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getSubRouteAllInfo(){
        redisCacheService.getSubRouteAllInfo();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询子线路表信息。
     */
    @RequestMapping(value = "getRouteNameBySubRouteId", method = RequestMethod.POST)
    @ResponseBody
    public void getRouteNameBySubRouteId(){
        redisCacheService.getRouteNameBySubRouteId();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询公交专用道信息。
     */
    @RequestMapping(value = "getMapBusSpecialRouteGeoData", method = RequestMethod.POST)
    @ResponseBody
    public void getMapBusSpecialRouteGeoData(){
        redisCacheService.getMapBusSpecialRouteGeoData();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 线路、子线单程数据
     */
    @RequestMapping(value = "getcacheMapSubRouteSegmentBySubRouteID", method = RequestMethod.POST)
    @ResponseBody
    public void getcacheMapSubRouteSegmentBySubRouteID(){
        redisCacheService.getcacheMapSubRouteSegmentBySubRouteID();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询子线路单程关系表信息。
     */
    @RequestMapping(value = "getMcsgeMentinfogs", method = RequestMethod.POST)
    @ResponseBody
    public void getMcsgeMentinfogs(){
        redisCacheService.getMcsgeMentinfogs();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 车辆信息缓存。
     */
    @RequestMapping(value = "getMcsgeBusInfogs", method = RequestMethod.POST)
    @ResponseBody
    public void getMcsgeBusInfogs() throws BusinessException {
        redisCacheService.getMcsgeBusInfogs();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询充电站表信息。
     */
    @RequestMapping(value = "getNeStationInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getNeStationInfo(){
        redisCacheService.getNeStationInfo();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 场站表信息。
     */
    @RequestMapping(value = "getSiteInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getSiteInfo(){
        redisCacheService.getSiteInfo();
    }

    /**
     * @return void
     * @description 查询线路图标配置Map。
     */
    @RequestMapping(value = "getRouteIcons", method = RequestMethod.POST)
    @ResponseBody
    public void getRouteIcons(){
        redisCacheService.getRouteIcons();
    }

    /**
     * 线路站点数据(线路ID,双程号，站点ID)
     */
    @RequestMapping(value = "getMcsgeRouteStationInfogs", method = RequestMethod.POST)
    @ResponseBody
    public void getMcsgeRouteStationInfogs() throws BusinessException{
        redisCacheService.getMcsgeRouteStationInfogs();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 查询单程线路站点信息表表信息。
     */
    @RequestMapping(value = "getSegmentStation", method = RequestMethod.POST)
    @ResponseBody
    public void getSegmentStation(){
        redisCacheService.getSegmentStation();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 监控报警区域。
     */
   /* @RequestMapping(value = "getAlarmArea", method = RequestMethod.POST)
    @ResponseBody
    public Boolean getAlarmArea(){
        return redisCacheService.getAlarmArea();
    }*/

    /**
     * @return List<BasicInfoEntity>
     * @description 监控报警区域。
     */
    @RequestMapping(value = "getMapStationToStation", method = RequestMethod.POST)
    @ResponseBody
    public void getMapStationToStation(){
        redisCacheService.getMapStationToStation();
    }

    /**
     * @return List<BasicInfoEntity>
     * @description 所有缓存数据更新。
     */
    @RequestMapping(value = "allRedisDataUpdate", method = RequestMethod.POST)
    @ResponseBody
    public void allRedisDataUpdate(){
        redisCacheService.routeGeoCache();
        redisCacheService.getStationInfo();
        redisCacheService.getRouteAllInfo();
        redisCacheService.getSubRouteAllInfo();
        redisCacheService.getRouteNameBySubRouteId();
        redisCacheService.getMapBusSpecialRouteGeoData();
        redisCacheService.getcacheMapSubRouteSegmentBySubRouteID();
        redisCacheService.getMcsgeMentinfogs();
        redisCacheService.getMcsgeBusInfogs();
        redisCacheService.getNeStationInfo();
        redisCacheService.getSiteInfo();
        redisCacheService.getRouteIcons();
        redisCacheService.getMcsgeRouteStationInfogs();
        redisCacheService.getSegmentStation();
        redisCacheService.getMapStationToStation();

    }

    /**
     * @return List<BasicInfoEntity>
     * @description redis取数据测试。
     */
    @RequestMapping(value = "redisTest", method = RequestMethod.POST)
    @ResponseBody
    public void redisTest(){
        Map mapString = RedisUtil.getMap(RedisKeyParamter.cacheMapRouteIcon,String.class);
        mapString.keySet();

        //Map<String,List<Map<String,Class>>>
        Map mapStr = RedisUtil.getListMap(RedisKeyParamter.cacheMapRouteStationByRouteId);
        mapStr.keySet();

        //Map<String,List<ClsSegmentInfo>>
        Map mapList = RedisUtil.getClassListMap(RedisKeyParamter.cacheMapSegmentInfoByRouteName,ClsSegmentInfo.class);
        mapList.keySet();

        //Map<String,List<ClsSegmentInfo>>
        Map mapListString = RedisUtil.getClassListMap(RedisKeyParamter.cacheMapSubRouteSegmentBySubRouteID,String.class);
        mapList.keySet();



    }

    /**
     * @return List<BasicInfoEntity>
     * @description kafka测试。
     */
    @RequestMapping(value = "kafkaTest", method = RequestMethod.POST)
    @ResponseBody
    public void kafkaTest(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.17.2.127:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<String, String>(properties);
//            producer.send(new ProducerRecord<String, String>("HelloWorld","aaaaaaaaaaaa"));
            for (int i = 0; i < 100; i++) {
                String msg = "This is Message " + i;
                producer.send(new ProducerRecord<String, String>("test", msg));
                System.out.println("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }
    }

    /**
     * @return List<BasicInfoEntity>
     * @description kafka测试。
     */
    @RequestMapping(value = "kafkaTest1", method = RequestMethod.POST)
    @ResponseBody
    public void kafkaTest1(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "172.17.2.127:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println("=====================>");
            }
        }
    }




}
