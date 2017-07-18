package web.log.monitor.biz.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class LocErrorStatisticDTO {


   private List<SS> list;

  public static class SS{

      private String name;
      private String value;

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getValue() {
         return value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }

}
