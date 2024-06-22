package com.tp.projectbase.entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link com.tp.projectbase.entity.Data}.
 * NOTE: This class has been automatically generated from the {@link com.tp.projectbase.entity.Data} original class using Vert.x codegen.
 */
public class DataConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Data obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "contentId":
          if (member.getValue() instanceof Number) {
            obj.setContentId(((Number)member.getValue()).longValue());
          }
          break;
        case "contentType":
          if (member.getValue() instanceof Number) {
            obj.setContentType(((Number)member.getValue()).intValue());
          }
          break;
        case "country":
          if (member.getValue() instanceof String) {
            obj.setCountry((String)member.getValue());
          }
          break;
        case "createdDate":
          if (member.getValue() instanceof Number) {
            obj.setCreatedDate(((Number)member.getValue()).longValue());
          }
          break;
        case "deviceId":
          if (member.getValue() instanceof String) {
            obj.setDeviceId((String)member.getValue());
          }
          break;
        case "eventType":
          if (member.getValue() instanceof String) {
            obj.setEventType((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof Number) {
            obj.setId(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Data obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Data obj, java.util.Map<String, Object> json) {
    if (obj.getContentId() != null) {
      json.put("contentId", obj.getContentId());
    }
    if (obj.getContentType() != null) {
      json.put("contentType", obj.getContentType());
    }
    if (obj.getCountry() != null) {
      json.put("country", obj.getCountry());
    }
    if (obj.getCreatedDate() != null) {
      json.put("createdDate", obj.getCreatedDate());
    }
    if (obj.getDeviceId() != null) {
      json.put("deviceId", obj.getDeviceId());
    }
    if (obj.getEventType() != null) {
      json.put("eventType", obj.getEventType());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
  }
}
