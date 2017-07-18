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