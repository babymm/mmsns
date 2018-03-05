package com.lovecws.mumu.mmsns.modular.streaming.flink;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Map;
import java.util.Properties;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 日志监控
 * @date 2018-03-05 14:53:
 */
public class MmsnsPortalLogStreaming {

    public void streaming() throws Exception {
        Properties properties = new Properties();
        properties.load(MmsnsPortalLogStreaming.class.getResourceAsStream("/properties/kafka.properties"));

        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("kafka.bootstrap.servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getProperty("kafka.client.id"));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        DataStream<String> streamSource = streamExecutionEnvironment.addSource(new FlinkKafkaConsumer09(properties.getProperty("kafka.client.topic"), new SimpleStringSchema(), props));
        SingleOutputStreamOperator<Tuple3<String, String, Integer>> outputStreamOperator = streamSource
                .flatMap(new FlatMapFunction<String, Tuple3<String, String, Integer>>() {
                    @Override
                    public void flatMap(String message, Collector<Tuple3<String, String, Integer>> collector) throws Exception {
                        Map<String, String> map = JSON.parseObject(message, Map.class);
                        collector.collect(new Tuple3<String, String, Integer>(map.get("typename"), map.get("method"), 1));
                    }
                })
                .keyBy(0, 1)
                .sum(2);
        outputStreamOperator.print().setParallelism(1);
        streamExecutionEnvironment.execute("MmsnsPortalLogStreaming streaming");
    }

    public static void main(String[] args) throws Exception {
        new MmsnsPortalLogStreaming().streaming();
    }
}
