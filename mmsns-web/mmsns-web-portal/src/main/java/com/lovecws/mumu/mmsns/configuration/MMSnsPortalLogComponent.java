package com.lovecws.mumu.mmsns.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lovecws.mumu.core.log.MumuLogComponent;
import com.lovecws.mumu.core.log.MumuLogEntity;
import com.lovecws.mumu.core.utils.DateUtils;
import com.lovecws.mumu.core.utils.PropertiesLoader;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 日志拦截器 将日志信息发送到kafka消息组件中
 * @date 2017-12-08 21:50
 */
@Aspect
@Component
public class MMSnsPortalLogComponent extends MumuLogComponent {

    private static final MmsnsKafkaLogProcedure kafkaLogProcedure = new MmsnsKafkaLogProcedure();
    private static final Logger log = Logger.getLogger(MMSnsPortalLogComponent.class);

    @Pointcut("execution(* com.lovecws.mumu.mmsns.controller.*.*.*Controller.*(..))")
    public void MMSnsPortalLogComponent() {
    }

    @Override
    public void record(MumuLogEntity mumuLogEntity) {
        //log.info(JSON.toJSONStringWithDateFormat(mumuLogEntity, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[]{SerializerFeature.QuoteFieldNames, SerializerFeature.WriteMapNullValue}));
        //使用kafka消息组件 将日志信息发送到kafka组件中
        try {
            kafkaLogProcedure.sendAsyncMessage(JSON.toJSONStringWithDateFormat(mumuLogEntity, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[]{SerializerFeature.QuoteFieldNames, SerializerFeature.WriteMapNullValue}));
        } catch (ExecutionException | InterruptedException e) {
            log.error("kafka消息发送失败");
        }
    }

    @Around("MMSnsPortalLogComponent()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        return log(joinPoint, false);
    }

    private static final class MmsnsKafkaLogProcedure {
        private String topic;
        private static KafkaProducer<Integer, String> producer = null;

        public MmsnsKafkaLogProcedure() {
            PropertiesLoader propertiesLoader = new PropertiesLoader("/properties/kafka.properties");
            this.topic = propertiesLoader.getProperty("kafka.client.topic");
            Properties props = new Properties();
            props.put("bootstrap.servers", propertiesLoader.getProperty("kafka.bootstrap.servers"));
            props.put("client.id", propertiesLoader.getProperty("kafka.client.id"));
            props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("retries", 3);//重试次数
            producer = new KafkaProducer<Integer, String>(props);
        }

        /**
         * 发送同步消息
         *
         * @param message 消息
         */
        public void sendMessage(String message) throws ExecutionException, InterruptedException {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<Integer, String>(topic, null, message)).get();
            log.info(recordMetadata + " " + message);
        }

        /**
         * 发送异步消息
         *
         * @param message 消息
         */
        public void sendAsyncMessage(String message) throws ExecutionException, InterruptedException {
            Object object = producer.send(new ProducerRecord<Integer, String>(topic, null, message), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    log.info(recordMetadata + " " + message);
                }
            }).get();
        }
    }
}
