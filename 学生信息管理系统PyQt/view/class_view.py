#班级管理页面
from PyQt5.QtWidgets import \
    QWidget, QLabel, \
    QLineEdit, QPushButton, QHBoxLayout, \
    QVBoxLayout, QTableWidget, QTableWidgetItem, QMessageBox
from tools.check_mes import *

class ClassInfo:
    def __init__(self, clsName, num):
        self.clsName = clsName
        self.num = num

class ClassManageWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()
        self.setFixedSize(self.width(), self.height())

    def initUI(self):
        self.setWindowTitle('班级管理')
        self.resize(300, 600)
        self.table_widget = QTableWidget()
        self.table_widget.setColumnCount(2)
        self.table_widget.setHorizontalHeaderLabels(['班级', '人数'])
        self.table_widget.setEditTriggers(QTableWidget.NoEditTriggers)
        self.table_widget.setSelectionBehavior(QTableWidget.SelectRows)

        button_add = QPushButton('添加')
        button_add.clicked.connect(self.on_button_add_clicked)
        button_delete = QPushButton('删除')
        button_delete.clicked.connect(self.on_button_delete_clicked)
        button_flush = QPushButton('刷新')
        button_flush.clicked.connect(self.on_button_flush_clicked)

        label_class = QLabel('班级:')
        self.edit_class = QLineEdit()

        hbox1 = QHBoxLayout()
        hbox1.addWidget(label_class)
        hbox2 = QHBoxLayout()
        hbox2.addWidget(self.edit_class)
        hbox3 = QHBoxLayout()
        hbox3.addWidget(button_add)
        hbox3.addWidget(button_delete)
        hbox3.addWidget(button_flush)
        vbox = QVBoxLayout()
        vbox.addWidget(self.table_widget)
        vbox.addLayout(hbox1)
        vbox.addLayout(hbox2)
        vbox.addLayout(hbox3)
        self.setLayout(vbox)

        # 初始化信息列表
        self.class_info_list = []
        sql = "select * from `class`"
        resultSet = query(sql, ())
        if resultSet is not None:
            for row in resultSet:
                self.class_info_list.append(ClassInfo(row[0], row[1]))
        self.show_class_info_list()

    def on_button_flush_clicked(self):
        self.class_info_list = []
        sql = "select * from `class`"
        resultSet = query(sql, ())
        if resultSet is not None:
            for row in resultSet:
                self.class_info_list.append(ClassInfo(row[0], row[1]))
        self.show_class_info_list()

    def show_class_info_list(self):
        self.table_widget.clearContents()
        self.table_widget.setRowCount(len(self.class_info_list))
        for i, info in enumerate(self.class_info_list):
            clsName_item = QTableWidgetItem(info.clsName)
            num_item = QTableWidgetItem(str(info.num))
            self.table_widget.setItem(i, 0, clsName_item)
            self.table_widget.setItem(i, 1, num_item)

    def on_button_add_clicked(self):
        clsName = self.edit_class.text()
        if clsName == '':
            QMessageBox.warning(None, '警告', '班级名不能为空', QMessageBox.Ok)
            return
        try:
            l = len(clsName)
            if l <= 0 or l > 10: raise ValueError()
        except:
            QMessageBox.warning(None, '警告', '班级名必须是0-10个字符的字符串', QMessageBox.Ok)
            return
        sql = "select * from `class`"
        resultSet = query(sql, ())
        for row in resultSet:
            if row[0] == clsName:
                QMessageBox.warning(None, '警告', '添加失败，' + clsName + '已存在', QMessageBox.Ok)
                return

        sql = "insert into `class` values(%s, %s)"
        execute(sql, (clsName, 0))
        info = ClassInfo(clsName, 0)
        self.class_info_list.append(info)
        self.show_class_info_list()

    def on_button_delete_clicked(self):
        rowNum = self.table_widget.currentRow()
        if rowNum < 0:
            QMessageBox.warning(None, '警告', '你没有选中任何行', QMessageBox.Ok)
            return
        sql = "delete from `class` where clsName=%s"
        execute(sql, (self.class_info_list[rowNum].clsName))
        del self.class_info_list[rowNum]
        self.show_class_info_list()