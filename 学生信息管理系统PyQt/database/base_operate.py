#本文件里存放用来操作数据库的函数
import pymysql

#获取数据库连接的函数
def _getConnect():
    return pymysql.connect(host="LAPTOP-TO4NDLPU", port=3306, user="root", password="abc123", database="stu_mes_manage", charset="utf8")

#用于增删改的函数
def execute(sql, values):
    db = _getConnect()
    cur = db.cursor()
    try:
        cur.execute(sql, values)
        db.commit()
        return True
    except:
        db.rollback()
        return False
    finally:
        cur.close()
        db.close()

#用于查询的函数
def query(sql, values):
    db = _getConnect()
    cur = db.cursor()
    try:
        cur.execute(sql, values)
        return cur.fetchall()
    except:
        return None
    finally:
        cur.close()
        db.close()
