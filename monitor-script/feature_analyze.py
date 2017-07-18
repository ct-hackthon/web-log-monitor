
from math import *
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.cluster import DBSCAN
from sklearn.cluster import SpectralClustering
import re
from itertools import cycle
import urllib.request as ur
import requests
import warnings
warnings.filterwarnings('ignore')

"""
特征工程
1.特征提炼
2.聚类+plot图
"""

'''
根据 url 提炼出具体的host
'''
def extractHost(full_link):
    regex = '((http|ftp|https|\s)://)[a-zA-Z\.0-9]+(?=\/)'
    pattern = re.compile(regex)
    match = pattern.match(str(full_link))
    if match:
        # print(match.group())
        return match.group()
    return None



'''
根据 url 提炼出具体的请求目标，排除非内部请求，处理资源文件请求
'''
def extractReqTarget(full_link):

    if "qunar" not in str(full_link):
        return None
    if "qrt=" in  str(full_link):
        return full_link.partition('qrt=')[2]
    if "html.ng" in  str(full_link):
        return 'qde'
    proto, rest = ur.splittype(full_link)
    res, rest = ur.splithost(rest)
    return None if not res else res


'''
输出 url:count
'''
def exportUrls(reqUrl_df):
    targetFile = open('../data/urls.txt', "a",encoding='UTF-8')
    reqUrl_df_value_counts = reqUrl_df.value_counts()
    data_len = len(reqUrl_df_value_counts.index)
    for i in range(data_len):
        targetFile.write(str(reqUrl_df_value_counts.index[i])+'    '+str(reqUrl_df_value_counts.values[i])+'\r')
    targetFile.close()

'''
输出文件，上传datav，用于可视化
'''
def exportHttpCodeNot200(df):
    train_data_dropnan_not200 =  df[df['httpCode'] != 200 ]
    train_data_dropnan_not200['type'] = 1 ##用于指定气泡样式
    train_data_dropnan_not200[['lat','lng','httpCode','hf','type']].to_csv("../data/httpcodenot200.csv",index=False)


def drawPlotForDBScan(df):
    x = np.array(df[['lat','lng']], dtype=np.float)
    per_city = 467.0994 #按边长为467.0994公里对中国切片
    # kms_per_radian = 6371.0088
    epsilon = 10/ per_city  #一直memoryError,网上按比例缩放后可运行
    db = DBSCAN(eps=epsilon,min_samples=10, algorithm='ball_tree', metric='haversine').fit(np.radians(x))
    labels = db.labels_
    unique_labels = set(labels)
    n_clusters_ = len(set(labels)) - (1 if -1 in labels else 0)
    colors = cycle('bgrcmykbgrcmykbgrcmykbgrcmyk')
    markers = []
    for i in range(n_clusters_):
        markers.append('o')

    for k , col , mar in zip(unique_labels , colors , markers):
        if k == -1:
            col = 'r'
        members = (labels == k )
        print( x[members, 0],x[members,1])
        plt.plot( x[members, 0] , x[members,1] , 'w', markerfacecolor=col, marker='.',markersize=8)
    plt.title(u'number of clusters: %d' % n_clusters_)
    plt.xlabel('lat')
    plt.ylabel('lng')
    plt.grid()
    plt.show()


def locatebyLatLon(lat, lng, pois=0):
    '''
    根据经纬度确定地址
    '''
    try:
        items = {'location': str(lat) + ',' + str(lng), 'ak': 'A9f77664caa0b87520c3708a6750bbdb', 'output': 'json'}
        if pois:
            items['pois'] = 1
        r = requests.get('http://api.map.baidu.com/geocoder/v2/', params=items)
        dictResult = r.json()
        return dictResult['result'] if not dictResult['status'] else None
    except Exception as e:
        print(str(e))
        return None


def toDateTime(ds):
    if '+' not in str(ds):
        return None
    clean_s = str(ds).split('+')[0]
    return pd.to_datetime(clean_s)

def getClusterId(dfdd,x):

    for loc_value,cluster_id_value in dfdd[['loc','cluster_id']].values:
        if x == loc_value:
            return cluster_id_value
    return -1


def spectralCluster(df):
    df_dropduplicates = df[['lat', 'lng','loc']].drop_duplicates()
    x = np.array(df_dropduplicates[['lat', 'lng']], dtype=np.float)
    sc = SpectralClustering(n_clusters=30, eigen_solver='arpack',
                            affinity="nearest_neighbors").fit(np.radians(x))
    labels = sc.labels_
    # print(labels)
    df_dropduplicates['cluster_id'] = labels
    df['cluster_id'] = df['loc'].apply(lambda x :getClusterId(df_dropduplicates,x))
    print(df[['loc','cluster_id']].values)

    newdf = df.groupby("cluster_id").mean()
    cluster_dict = {}
    for i, j in newdf.iterrows():
        ret = locatebyLatLon(j['lng'], j['lat'])
        if ret:
            city = ret['addressComponent']['province'] + "," + ret['addressComponent']['city']
            cluster_dict[i] = city
        else:
            cluster_dict[i] = None
    print (cluster_dict)
    return df['cluster_id'].replace(cluster_dict)



if __name__== "__main__":
    global defaultTrainData
    # try:
    #     for i in range(3,25):

    #         url = "../data/data"+str(i)+".txt"   ##数据地址
    #         print(url)
    #         defaultTrainData = pd.read_csv(url,sep='$')  ##读取文件
    #         '''
    #         占时提取这些特征进行处理，后续可以自己加上， 将none值去除，以后可考虑进行填充
    #         '''
    #         train_data_dropnan = defaultTrainData[['loc','httpCode','reqUrl','reqTime','mno','resSize','netType','uid','pid','hf','X-Date']]

    #         train_data_dropnan['httpCode'] = train_data_dropnan['httpCode'].fillna(200)

    #         train_data_dropnan['hf'] = train_data_dropnan['hf'].apply(lambda x: None if str(x) else "service_error")
    #         train_data_dropnan['hf'] = train_data_dropnan['hf'].dropna()


    #         ##可能反了，但不影响具体训练
    #         ##lat提取
    #         train_data_dropnan['lat'] = train_data_dropnan['loc'].apply(lambda x: float(str(x).split(',')[0]) if ',' in str(x) else 0)
    #         ##lng提取
    #         train_data_dropnan['lng'] = train_data_dropnan['loc'].apply(lambda x: float(str(x).split(',')[1]) if ',' in str(x) else 0)
    #         ##host提取
    #         train_data_dropnan['host'] = train_data_dropnan['reqUrl'].apply(lambda x:extractHost(str(x)))
    #         ##reqTarget提取
    #         train_data_dropnan['reqTarget'] = train_data_dropnan['reqUrl'].apply(lambda x:extractReqTarget(str(x)))

    #         train_data_dropnan['X-Date'] = train_data_dropnan['X-Date'].apply(lambda x: toDateTime(x))

    #         train_data_dropnan['minute'] = train_data_dropnan['X-Date'].apply(lambda x: x.minute)

    #         train_data_dropnan.sort(['X-Date'],ascending=True)

    #         train_data_dropnan['city'] = None
    #         for j in range(60):
    #             print(j)
    #             train_data_dropnan_permin = train_data_dropnan[train_data_dropnan['minute'] == j];
    #             loc_data_permin = train_data_dropnan_permin[['loc', 'minute']].dropna()
    #             loc_data_permin['lat'] = loc_data_permin['loc'].apply(lambda x: float(str(x).split(',')[0]))
    #             ##lng提取
    #             loc_data_permin['lng'] = loc_data_permin['loc'].apply(lambda x: float(str(x).split(',')[1]))
    #             # for k in range(0, loc_data.size, 2000):
    #             replace_item = spectralCluster(loc_data_permin)
    #             train_data_dropnan_permin['city'] = replace_item
    #             print(train_data_dropnan_permin['X-Date'].values)
    #             file_name = train_data_dropnan_permin['X-Date'].iloc[0].strftime('%y-%m-%d-%H-%M')
    #             print(file_name)
    #             train_data_dropnan_permin.to_csv("../input/"+str(file_name)+".csv",index=False,sep='$',encoding="utf-8")

    #         defaultTrainData = None
    # except Exception as e:
    #     print(str(e))       
    url = "../data/data0.txt"   ##数据地址
    print(url)
    defaultTrainData = pd.read_csv(url,sep='$')  ##读取文件
    '''
    占时提取这些特征进行处理，后续可以自己加上， 将none值去除，以后可考虑进行填充
    '''
    train_data_dropnan = defaultTrainData[['loc','httpCode','reqUrl','reqTime','mno','resSize','netType','uid','pid','hf','X-Date']]

    train_data_dropnan['httpCode'] = train_data_dropnan['httpCode'].fillna(200)

    train_data_dropnan['hf'] = train_data_dropnan['hf'].apply(lambda x: None if str(x) else "service_error")
    train_data_dropnan['hf'] = train_data_dropnan['hf'].dropna()


    ##可能反了，但不影响具体训练
    ##lat提取
    train_data_dropnan['lat'] = train_data_dropnan['loc'].apply(lambda x: float(str(x).split(',')[0]) if ',' in str(x) else 0)
    ##lng提取
    train_data_dropnan['lng'] = train_data_dropnan['loc'].apply(lambda x: float(str(x).split(',')[1]) if ',' in str(x) else 0)
    # ##host提取
    # train_data_dropnan['host'] = train_data_dropnan['reqUrl'].apply(lambda x:extractHost(str(x)))
    # ##reqTarget提取
    # train_data_dropnan['reqTarget'] = train_data_dropnan['reqUrl'].apply(lambda x:extractReqTarget(str(x)))

    # train_data_dropnan['X-Date'] = train_data_dropnan['X-Date'].apply(lambda x: toDateTime(x))

    # train_data_dropnan['minute'] = train_data_dropnan['X-Date'].apply(lambda x: x.minute)
    drawPlotForDBScan(train_data_dropnan)











