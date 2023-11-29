import pandas as pd
import numpy as np

cleanExcelPath = "data/cleanData.xlsx"
filterData = pd.read_excel(cleanExcelPath)

# 计算每个用户的F值，新增F列用于存放F值，算出的是频数
filterData['F'] = filterData.groupby('买家会员名')['买家会员名'].transform('count')
# 转换成频率
filterData['F'] = filterData['F'] / filterData['买家会员名'].size

# 只保留最近一次购物记录，把更早的记录过滤删除掉
filterData['最近购物日期'] = filterData.groupby('买家会员名')['订单付款时间'].transform('max')
filterData = filterData[filterData['订单付款时间'] == filterData['最近购物日期']]
filterData = filterData.drop(columns=['最近购物日期'])
# 处理同一个用户一次购买多件商品的情况
filterData.drop_duplicates(keep='first', inplace=True)

# 计算每个用户的R值，新增R列用于存放R值
filterData['R'] = (pd.to_datetime(filterData['数据采集时间']) - pd.to_datetime(filterData['订单付款时间'])).values / np.timedelta64(1, "D")

# 计算每个用户的M值，新增M列用于存放M值，这里M值取每个用户最近一次的消费金额
filterData['M'] = filterData['买家实际支付金额']

# 为了方便观察，交换F和R列的位置和列名
filterData[['F', 'R']] = filterData[['R', 'F']]
filterData.rename(columns={'F':'R', 'R':'F'}, inplace=True)

# 删除辅助列，只保留RFM三列和名字列
for s in ['订单付款时间', '买家实际支付金额', '数据采集时间']:
    filterData.drop(columns=[s], inplace=True)

# 以下是做数据归一化处理
# 计算RFM三列的平均值
rMean = filterData['R'].mean()
fMean = filterData['F'].mean()
mMean = filterData['M'].mean()
# 计算RFM三列的标准差
rSigma = filterData['R'].std(ddof=0)
fSigma = filterData['F'].std(ddof=0)
mSigma = filterData['M'].std(ddof=0)

# 对RFM三列做数据归一化，(每个特征值 - 本列的均值) / 本列的样本标准差
filterData['R'] = (filterData['R'] - rMean) / rSigma
filterData['F'] = (filterData['F'] - fMean) / fSigma
filterData['M'] = (filterData['M'] - mMean) / mSigma

# 把dataframe转换成excel表格输出到data文件夹
filterData.to_excel('data/transformData.xlsx', index=False)