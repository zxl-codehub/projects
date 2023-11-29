import pandas as pd
import numpy as np

initExcelPath = "data/initData.xlsx"
dataFrame = pd.read_excel(initExcelPath)
filterCol = ['订单付款时间', '买家会员名', '买家实际支付金额', '数据采集时间']
filterData = dataFrame[filterCol]

maxAmount = filterData.max()['买家实际支付金额']
minAmount = filterData.min()['买家实际支付金额']

size = []
for str in filterCol:
    df = filterData.isnull()[str]
    count = 0
    for b in df:
        if b:
            count += 1
    size.append(count)

res = pd.DataFrame([
    [size[0], np.nan, np.nan],
    [size[1], np.nan, np.nan],
    [size[2], maxAmount, minAmount],
    [size[3], np.nan, np.nan]
],columns=['缺失值', '最大值', '最小值'], index=filterCol)

res.to_excel('data/view.xlsx')