import pandas as pd
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import seaborn as sn

transformExcelPath = 'data/transformData.xlsx'
dataFrame0 = pd.read_excel(transformExcelPath)
dataFrame = dataFrame0.drop(columns=['买家会员名'])

# 聚成4类
kModel = KMeans(n_clusters=4, max_iter=500)
kModel.fit(dataFrame)

# 把kmeans结果作为新列放入原表，新列名为 聚类类别
dataFrame0['聚类类别'] = kModel.labels_

# 统计各类分别有几个数据，统计结果放入clsNum数据框架中
clsMsg = pd.DataFrame(dataFrame0['聚类类别'].value_counts())
clsMsg.sort_values(by='聚类类别', inplace=True)
clsMsg.columns = ['聚类数量']

# 获取4个类的质心
for i in range(3):
    col = []
    for j in range(4):
        col.append(kModel.cluster_centers_[j][i])
    clsMsg['RFM'[i]] = pd.DataFrame(col)

# 生成文件
dataFrame0.to_excel('data/dataType.xlsx', index=False)
clsMsg.to_excel('data/classMessage.xlsx')

# 以下是作图部分
# 数据准备
# 把dataFrame0按类别分为4组记录
cls0 = dataFrame0[dataFrame0['聚类类别'] == 0]
cls1 = dataFrame0[dataFrame0['聚类类别'] == 1]
cls2 = dataFrame0[dataFrame0['聚类类别'] == 2]
cls3 = dataFrame0[dataFrame0['聚类类别'] == 3]
# 获取每组数据的RFM值，把数据封装成字典，方便使用
rfm = {
    'cls0' : {
        'R' : cls0['R'].tolist(),
        'F' : cls0['F'].tolist(),
        'M' : cls0['M'].tolist()
    },
    'cls1' : {
        'R' : cls1['R'].tolist(),
        'F' : cls1['F'].tolist(),
        'M' : cls1['M'].tolist()
    },
    'cls2' : {
        'R' : cls2['R'].tolist(),
        'F' : cls2['F'].tolist(),
        'M' : cls2['M'].tolist()
    },
    'cls3' : {
        'R' : cls3['R'].tolist(),
        'F' : cls3['F'].tolist(),
        'M' : cls3['M'].tolist()
    }
}

# 防止作图中文乱码，以及不显示负数
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

# 绘制4个子图
fig0,ax0 = plt.subplots(3,1)
ax0[0].set_title('客户群=0;聚类数量=' + str(clsMsg.iloc[0,0]))
sn.kdeplot(rfm['cls0']['R'], ax=ax0[0], color='deeppink').legend(loc='upper right', labels='R')
sn.kdeplot(rfm['cls0']['F'], ax=ax0[1], color='orange').legend(loc='upper right', labels='F')
sn.kdeplot(rfm['cls0']['M'], ax=ax0[2], color='yellowgreen').legend(loc='upper right', labels='M')

fig1,ax1 = plt.subplots(3,1)
ax1[0].set_title('客户群=1;聚类数量=' + str(clsMsg.iloc[1,0]))
sn.kdeplot(rfm['cls1']['R'], ax=ax1[0], color='deeppink').legend(loc='upper right', labels='R')
sn.kdeplot(rfm['cls1']['F'], ax=ax1[1], color='orange').legend(loc='upper right', labels='F')
sn.kdeplot(rfm['cls1']['M'], ax=ax1[2], color='yellowgreen').legend(loc='upper right', labels='M')

fig2,ax2 = plt.subplots(3,1)
ax2[0].set_title('客户群=2;聚类数量=' + str(clsMsg.iloc[2,0]))
sn.kdeplot(rfm['cls2']['R'], ax=ax2[0], color='deeppink').legend(loc='upper right', labels='R')
sn.kdeplot(rfm['cls2']['F'], ax=ax2[1], color='orange').legend(loc='upper right', labels='F')
sn.kdeplot(rfm['cls2']['M'], ax=ax2[2], color='yellowgreen').legend(loc='upper right', labels='M')

fig3,ax3 = plt.subplots(3,1)
ax3[0].set_title('客户群=3;聚类数量=' + str(clsMsg.iloc[3,0]))
sn.kdeplot(rfm['cls3']['R'], ax=ax3[0], color='deeppink').legend(loc='upper right', labels='R')
sn.kdeplot(rfm['cls3']['F'], ax=ax3[1], color='orange').legend(loc='upper right', labels='F')
sn.kdeplot(rfm['cls3']['M'], ax=ax3[2], color='yellowgreen').legend(loc='upper right', labels='M')

plt.show()