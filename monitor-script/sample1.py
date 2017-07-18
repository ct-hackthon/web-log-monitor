import json
import pandas as pd


if __name__ == '__main__':    
    print("hello python")
    file_object = open('../data/full_data/iosMonitorLog.log.2017-04-01-00','r',encoding= 'UTF-8')
    try:
        i = 0
        for line in file_object:
            i=i+1
            lineline = line.rstrip()
            end_index = lineline.index('###')
            str = lineline[19:end_index]
            json_obj = json.loads(str)
            network_list = json_obj.get("network")
            if(network_list is not None):
                for network_obj in network_list:
                    print("第",i,"行","*******************")
                    for key in network_obj.keys():
                        print(network_obj.get(key))
    finally:
        file_object.close()