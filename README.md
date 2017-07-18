# 目录结构 #

|__.ipynb_checkpoints //jupyter_notebook 内置保存点副本，不用提交到git
|__data               // 清洗后数据存放
   |__
|__ref                
   |__db              // DB 脚本 和 note 所在位置
   |___notebook       // jupyter book 本次课题研究的notebook
|__src                // python 脚本 所在地
|__log                // 如果需要输出日志 存放在这里
|__monitor-alert      // 后端代码
|__monitor-display    // 前端代码
|__.gitignore


# 环境搭建 #

python 3.6.1

pandas，matplotlib, scikit-learn，numpy，json





# 数据 #

提取了一小时的数据
如下是httpCode的统计

 200    917884
 401      1561
-1         956
 404       674
 502        90
 403        73
 504        16
 503         9
 596         8
 500         7
 415         1
 201         1
 592         1
Name: httpCode, dtype: int64

如下是hf的统计:
似乎已断开与互联网的连接。                                                           21825
请求超时。                                                                    9434
发生了 SSL 错误，无法建立与该服务器的安全连接。                                               6471
已取消                                                                      4905
无法建立数据连接，因为通话仍在进行中。                                                      3247
网络连接已中断。                                                                 2621
The operation couldn’t be completed. (NSURLErrorDomain error -1200.)     1142
未能找到使用指定主机名的服务器。                                                          755
未能连接到服务器。                                                                 461
未能完成该操作。协议错误                                                              394
无法解析响应                                                                    155
国际漫游目前已关闭。                                                                147
The operation couldn’t be completed. (NSURLErrorDomain error -1001.)      137
The operation couldn’t be completed. (NSURLErrorDomain error -1009.)      101
未能完成该操作。连接被对方重置                                                            59
目前不允许数据连接。                                                                 53
未能完成该操作。Connection reset by peer                                           38
The operation couldn’t be completed. (NSURLErrorDomain error -1005.)       37
The operation couldn’t be completed. Connection reset by peer              36
未能完成操作。（“NSURLErrorDomain”错误 -1009。）                                       33
未能完成该操作。错误的文件描述符                                                           32
The operation couldn’t be completed. (NSURLErrorDomain error -1019.)       26
未能完成该操作。Bad file descriptor                                                24
未能完成该操作。Protocol error                                                     23
未能完成操作。（“NSURLErrorDomain”错误 -1001。）                                       20
不支持的 URL                                                                   16
未能完成该操作。Invalid argument                                                    9
此服务器的证书无效。您可能正在连接到一个伪装成“client.qunar.com”的服务器，这会威胁到您的机密信息的安全。               9
未能完成该操作。操作已取消                                                               8
未能完成操作。（“kCFErrorDomainCFNetwork”错误 310。）                                   8
The operation couldn’t be completed. (NSURLErrorDomain error -1004.)        8
The operation couldn’t be completed. (NSURLErrorDomain error -1003.)        7
未能完成操作。（“NSURLErrorDomain”错误 -1019。）                                        4
The operation couldn’t be completed. Invalid argument                       3
The operation couldn’t be completed. Bad file descriptor                    2
未能完成操作。（“NSURLErrorDomain”错误 -1003。）                                        2
未能完成操作。（“NSURLErrorDomain”错误 -1200。）                                        2
The operation couldn’t be completed. (NSURLErrorDomain error -1020.)        1
未能完成该操作。socket 不支持此操作                                                       1
未能完成操作。（“kCFErrorDomainCFNetwork”错误 303。）                                   1
The operation couldn’t be completed. (NSURLErrorDomain error -1002.)        1
未能完成操作。（“NSURLErrorDomain”错误 -1008。）                                        1
未能完成操作。（“NSURLErrorDomain”错误 -1005。）                                        1 

https://cdrive.cloud.ctripcorp.com/s/cHkd3LZIvhgdqPC




['The operation couldn\\U2019t be completed. (NSURLErrorDomain error -1001.)',
'The operation couldn\\U2019t be completed. (NSURLErrorDomain error -1003.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1001.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1002.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1003.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1004.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1005.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1008.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1009.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1017.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1018.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1019.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1020.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1200.)',
'The operation couldn’t be completed. (NSURLErrorDomain error -1202.)',
'The operation couldn’t be completed. (kCFErrorDomainCFNetwork error 303.)',
'The operation couldn’t be completed. Bad file descriptor',
'The operation couldn’t be completed. Connection reset by peer',
'The operation couldn’t be completed. Invalid argument',
'The operation couldn’t be completed. No such file or directory',
'The request timed out.',
'不支持的 URL',
'似乎已断开与互联网的连接。',
'发生了 SSL 错误，无法建立与该服务器的安全连接。',
'国际漫游目前已关闭。',
'太多 HTTP 重定向',
'已取消',
'无法建立数据连接，因为通话仍在进行中。',
'无法解析响应',
'未知错误',
'未能完成操作。（“NSURLErrorDomain”错误 -1001。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1002。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1003。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1004。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1005。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1008。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1009。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1012。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1017。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1019。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1020。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1200。）',
'未能完成操作。（“NSURLErrorDomain”错误 -1202。）',
'未能完成操作。（“kCFErrorDomainCFNetwork”错误 303。）',
'未能完成操作。（“kCFErrorDomainCFNetwork”错误 310。）',
'未能完成该操作。Bad file descriptor',
'未能完成该操作。Connection reset by peer',
'未能完成该操作。Invalid argument',
'未能完成该操作。No such file or directory',
'未能完成该操作。Protocol error',
'未能完成该操作。socket 不支持此操作',
'未能完成该操作。协议族不支持地址族',
'未能完成该操作。协议错误',
'未能完成该操作。操作已取消',
'未能完成该操作。连接被对方重置',
'未能完成该操作。错误的文件描述符',
'未能找到使用指定主机名的服务器。',
'未能连接到服务器。',
'此服务器的证书无效。您可能正在连接到一个伪装成“123.59.180.160”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“api.map.baidu.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“client.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“direction2.is.autonavi.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“gsp-ssl.ls.apple.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“img1.qunarzz.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“mpkq.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“pictureserver.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“qde.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“searchtouch.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“source.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“source.qunarzz.com”的服务器，这会威胁到您的机密信息的安全。',
'此服务器的证书无效。您可能正在连接到一个伪装成“touch.qunar.com”的服务器，这会威胁到您的机密信息的安全。',
'目前不允许数据连接。',
'网络连接已中断。',
'请求超时。',
'错误的 URL']
目前数据中的所有hf种类



SELECT * FROM test.loc_error_statistic;
SELECT * FROM test.http_error_statistic;
SELECT * FROM test.mno_error_statistic;
SELECT * FROM test.pid_error_statistic;
SELECT * FROM test.url_error_statistic;
SELECT * FROM test.city_error_statistic;
SELECT * FROM test.http_code_statistic;

truncate test.http_error_statistic;
truncate test.pid_error_statistic;
truncate test.mno_error_statistic;
truncate test.url_error_statistic;
truncate test.loc_error_statistic;
truncate test.http_code_statistic;
truncate test.city_error_statistic;