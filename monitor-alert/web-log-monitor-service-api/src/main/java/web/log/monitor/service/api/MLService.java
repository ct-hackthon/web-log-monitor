package web.log.monitor.service.api;


import web.log.monitor.service.entity.HttpErrorStatisticData;

public interface MLService {

        void httpErrorPredict(HttpErrorStatisticData data);
}
