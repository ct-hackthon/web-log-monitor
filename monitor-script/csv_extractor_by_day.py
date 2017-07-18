# -*- coding:utf-8 -*-
import json
import os
import re
import gzip
import time
import sys


is_print_header = True
rootdir = '..\\ref'

def un_gz(file_name):
    f_name = file_name.replace(".gz", "")
    g_file = gzip.GzipFile(file_name)
    open(f_name, "wb+").write(g_file.read())
    g_file.close()

def extractor(target_file_name,source_file_name):
    targetFile = open(target_file_name, "a",encoding='UTF-8')
    for line in open(source_file_name, "r",encoding='UTF-8'):
        proceeLine(line,targetFile)


    targetFile.close()

def proceeLine(line_txt,target_file):

    global is_print_header
    upload_user_req = ''
    if '####' in line_txt:
        upload_user_req += line_txt.partition('####')[2]
        line_txt = line_txt.partition('####')[0]
    if '>>' in line_txt:
        line_txt = line_txt.partition('>>')[2]
    line_obj = json.loads(line_txt)
    upload_user_req_header,upload_user_req_value = processUploadReq(upload_user_req)
    try:
        for req in line_obj['network']:
            header = ''
            req_values = ''
            for k,v in req.items():
                if is_print_header:
                    header += k+','
                req_values += v+','


            if is_print_header :
                header += upload_user_req_header
                target_file.write(header[:header.rindex(',')]+'\n')
                is_print_header = False
            req_values += upload_user_req_value
            target_file.write(req_values[:req_values.rindex(',')])
    except:
        print(" There is no network module")



def processUploadReq(req):
    key = ''
    value = ''
    req_params = req.split('&')
    for req_param in req_params:
        req_params_pair = req_param.split('=')
        key += req_params_pair[0]+','
        if len(req_params_pair) == 2:
            value += req_params_pair[1]+','
        else:
            value += ','
    return key,value


if __name__== "__main__":
    print(" data processing is start at ",time.ctime(time.time()))
    for parent,dirnames,filenames in os.walk(rootdir):
        for filename in filenames:
            if filename.endswith('.gz'):
                full_file_name = os.path.join(parent,filename)
                print("ungzip file :",full_file_name)
                un_gz(full_file_name)
    print("1.ungzip data files is done")
    for parent,dirnames,filenames in os.walk(rootdir):
        for filename in filenames:
            if not filename.endswith('.gz'):
                full_file_name = os.path.join(parent,filename)
                print("extract data from zipfiles")
                target_name = "..\\data\\" + filename + ".txt"
                extractor(target_name,full_file_name)
    print("2.extract data files is done")
    print(" data processing is end at ",time.ctime(time.time()))
