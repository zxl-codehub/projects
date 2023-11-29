#本文件存放工具函数
from database.base_operate import *

#注册时，检测输入的用户名、密码是否正确
def check_userId_and_pwd(userName, pwd1, pwd2):
    if pwd1 != pwd2:
        return 1 #两次输入的密码不相同
    if userName == "" or pwd1 == "":
        return 2 #输入值为空串
    if userName is None or pwd1 is None:
        return 3 #输入值为空

    sql = "select * from user where userID=%s and pwd=%s"
    resultSet = query(sql, (userName, pwd1))
    if len(resultSet) != 0:
        return 4 #用户已存在
    else:
        sql = "insert into user values(%s, md5(%s))"
        execute(sql, (userName, pwd1))
        return 5 #注册成功

#登录时，检查输入的用户名、密码是否正确
def check_sign_in(userName, pwd):
    if userName == "" or pwd == "":
        return 1 #输入值为空串
    if userName is None or pwd is None:
        return 2 #输入值为空

    sql = "select * from user where userId=%s and pwd=md5(%s)"
    resultSet = query(sql, (userName, pwd))
    if len(resultSet) != 0:
        return 3  #登录成功
    else:
        return 4  #登录失败

#检查班级是否存在
def check_class_is_exist(clsName):
    sql = "select clsName from `class` where clsName=%s"
    resultSet = query(sql, (clsName))
    return len(resultSet) != 0