import pandas as pd

initExcelPath = "data/initData.xlsx"
dataFrame = pd.read_excel(initExcelPath)
filterCol = ['订单付款时间', '买家会员名', '买家实际支付金额', '数据采集时间']
filterData = dataFrame[filterCol]

# 过滤掉 订单付款时间为空 或 买家实际支付金额为0 的行
filterData = filterData[filterData['订单付款时间'].notnull() & (filterData['买家实际支付金额'] != 0)]

filterData.to_excel('data/cleanData.xlsx', index=False)