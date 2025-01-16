package authservice.serializer;

import authservice.eventProducer.UserInfoEvent;
import authservice.model.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoEvent> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
       // Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String arg0, UserInfoEvent arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retVal;
    }

   /* @Override
    public byte[] serialize(String topic, Headers headers, UserInfoDto data) {
       // return Serializer.super.serialize(topic, headers, data);
    }*/

    @Override
    public void close(){

    }
}
