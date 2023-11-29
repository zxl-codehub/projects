#本文件存放一些用户服务函数
from database.base_operate import *

#用于修改密码的函数
def modifyPwd(userId, oldPwd, pwd1, pwd2):
    if userId == '' or oldPwd == '' or pwd1 == '' or pwd2 == '':
        return 1 #未输入完整信息
    if userId is None or oldPwd is None or pwd1 is None or pwd2 is None:
        return 1 #未输入完整信息
    if pwd1 != pwd2:
        return 2 #两次输入的新密码不相同
    if oldPwd == pwd1:
        return 3 #新密码和旧密码相同

    sql = "select * from user where userID=%s and pwd=md5(%s)"
    resultSet = query(sql, (userId, oldPwd))
    if len(resultSet) == 0:
        return 4 #旧密码错误

    sql = "update user set pwd=md5(%s) where userID=%s"
    execute(sql, (pwd1, userId))
    return 5 #修改成功
