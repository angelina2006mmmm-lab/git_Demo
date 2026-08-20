package com.example.studentservice.config;
import com.example.studentservice.event.StudentCreatedEvent; import org.apache.kafka.clients.producer.ProducerConfig; import org.apache.kafka.common.serialization.StringSerializer; import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.kafka.core.*; import org.springframework.kafka.support.serializer.JsonSerializer; import java.util.*;
@Configuration public class KafkaProducerConfig {
 @Bean ProducerFactory<String,StudentCreatedEvent> producerFactory(@Value("${spring.kafka.bootstrap-servers}") String bs){Map<String,Object> p=new HashMap<>();p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bs);p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);p.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,false);return new DefaultKafkaProducerFactory<>(p);}
 @Bean KafkaTemplate<String,StudentCreatedEvent> kafkaTemplate(ProducerFactory<String,StudentCreatedEvent> pf){return new KafkaTemplate<>(pf);}
}
