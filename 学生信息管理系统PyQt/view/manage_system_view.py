#学生信息管理页面
from PyQt5.QtWidgets import \
    QWidget, QLabel, \
    QLineEdit, QPushButton, QHBoxLayout, \
    QVBoxLayout, QTableWidget, QTableWidgetItem, QMessageBox
from tools.check_mes import *

class StudentInfo:
    def __init__(self, id, name, class_, gender, age, address, phone):
        self.id = id
        self.name = name
        self.class_ = class_
        self.gender = gender
        self.age = age
        self.address = address
        self.phone = phone

class StuManageWindow(QWidget):
    orderNum = -1

    def __init__(self):
        super().__init__()
        self.initUI()
        self.setFixedSize(self.width(), self.height())

    def initUI(self):
        self.setWindowTitle('学生信息管理')
        self.resize(923, 600)
        self.table_widget = QTableWidget()
        self.table_widget.setColumnCount(7)
        self.table_widget.setHorizontalHeaderLabels(['学生编号', '姓名', '班级', '性别', '年龄', '家庭住址', '电话'])
        self.table_widget.setEditTriggers(QTableWidget.NoEditTriggers)
        self.table_widget.setSelectionBehavior(QTableWidget.SelectRows)
        self.table_widget.doubleClicked.connect(self.on_table_double_clicked)

        button_add = QPushButton('添加')
        button_add.clicked.connect(self.on_button_add_clicked)
        button_delete = QPushButton('删除')
        button_delete.clicked.connect(self.on_button_delete_clicked)
        button_update = QPushButton('修改')
        button_update.clicked.connect(self.on_button_update_clicked)
        button_clear = QPushButton('清空所有文本框')
        button_clear.clicked.connect(self.on_button_clear_clicked)

        label_id = QLabel('学生编号:')
        self.edit_id = QLineEdit()
        label_name = QLabel('姓名:')
        self.edit_name = QLineEdit()
        label_class = QLabel('班级:')
        self.edit_class = QLineEdit()
        label_gender = QLabel('性别:')
        self.edit_gender = QLineEdit()
        label_age = QLabel('年龄:')
        self.edit_age = QLineEdit()
        label_address = QLabel('家庭住址:')
        self.edit_address = QLineEdit()
        label_phone = QLabel('电话:')
        self.edit_phone = QLineEdit()

        hbox1 = QHBoxLayout()
        hbox1.addWidget(label_id)
        hbox1.addWidget(self.edit_id)
        hbox1.addWidget(label_name)
        hbox1.addWidget(self.edit_name)
        hbox1.addWidget(label_class)
        hbox1.addWidget(self.edit_class)
        hbox1.addWidget(label_gender)
        hbox1.addWidget(self.edit_gender)
        hbox2 = QHBoxLayout()
        hbox2.addWidget(label_age)
        hbox2.addWidget(self.edit_age)
        hbox2.addWidget(label_address)
        hbox2.addWidget(self.edit_address)
        hbox2.addWidget(label_phone)
        hbox2.addWidget(self.edit_phone)
        hbox3 = QHBoxLayout()
        hbox3.addWidget(button_add)
        hbox3.addWidget(button_delete)
        hbox3.addWidget(button_update)
        hbox3.addWidget(button_clear)
        vbox = QVBoxLayout()
        vbox.addWidget(self.table_widget)
        vbox.addLayout(hbox1)
        vbox.addLayout(hbox2)
        vbox.addLayout(hbox3)
        self.setLayout(vbox)

        #初始化信息列表
        self.student_info_list = []
        sql = "select stuID, stuName, `class`, gender, age, address, phone from student"
        resultSet = query(sql, ())
        if resultSet is not None:
            for row in resultSet:
                self.student_info_list.append(StudentInfo(row[0], row[1], row[2], row[3], row[4], row[5], row[6]))
        self.show_student_info_list()

    def show_student_info_list(self):
        self.table_widget.clearContents()
        self.table_widget.setRowCount(len(self.student_info_list))
        for i, info in enumerate(self.student_info_list):
            id_item = QTableWidgetItem(info.id)
            name_item = QTableWidgetItem(info.name)
            class_item = QTableWidgetItem(info.class_)
            gender_item = QTableWidgetItem(info.gender)
            age_item = QTableWidgetItem(str(info.age))
            address_item = QTableWidgetItem(info.address)
            phone_item = QTableWidgetItem(info.phone)
            self.table_widget.setItem(i, 0, id_item)
            self.table_widget.setItem(i, 1, name_item)
            self.table_widget.setItem(i, 2, class_item)
            self.table_widget.setItem(i, 3, gender_item)
            self.table_widget.setItem(i, 4, age_item)
            self.table_widget.setItem(i, 5, address_item)
            self.table_widget.setItem(i, 6, phone_item)

    def on_button_clear_clicked(self):
        self.edit_id.setText('')
        self.edit_name.setText('')
        self.edit_class.setText('')
        self.edit_gender.setText('')
        self.edit_age.setText('')
        self.edit_address.setText('')
        self.edit_phone.setText('')

    def on_table_double_clicked(self):
        rowNum = self.table_widget.currentRow()
        info = self.student_info_list[rowNum]
        self.orderNum = rowNum
        self.edit_id.setText(info.id)
        self.edit_name.setText(info.name)
        self.edit_class.setText(info.class_)
        self.edit_gender.setText(info.gender)
        self.edit_age.setText(str(info.age))
        self.edit_address.setText(info.address)
        self.edit_phone.setText(info.phone)

    def on_button_add_clicked(self):
        id = self.edit_id.text()
        try:
            if id == '': raise ValueError()
            int(id)
        except:
            QMessageBox.warning(None, '警告', '学生编号不能为空，且只能是纯数字', QMessageBox.Ok)
            return

        name = self.edit_name.text()
        if name == '':
            QMessageBox.warning(None, '警告', '姓名不能为空', QMessageBox.Ok)
            return

        class_ = self.edit_class.text()
        if class_ == '':
            QMessageBox.warning(None, '警告', '班级不能为空', QMessageBox.Ok)
            return
        if not check_class_is_exist(class_):
            QMessageBox.warning(None, '警告', class_+'不存在', QMessageBox.Ok)
            return

        gender = self.edit_gender.text()
        if gender == '' or (gender != '男' and gender != '女'):
            QMessageBox.warning(None, '警告', '性别不正确，只能为\'男\'或\'女\'', QMessageBox.Ok)
            return

        try:
            age = int(self.edit_age.text())
            if age < 0: raise ValueError()
        except:
            QMessageBox.warning(None, '警告', '年龄不正确，应为大于等于0的整数', QMessageBox.Ok)
            return

        address = self.edit_address.text()
        if address == '':
            QMessageBox.warning(None, '警告', '家庭地址不能为空', QMessageBox.Ok)
            return

        phone = self.edit_phone.text()
        try:
            if phone == '': raise ValueError()
            int(phone)
            if len(phone) != 11: raise ValueError()
        except:
            QMessageBox.warning(None, '警告', '电话号码不能为空，且只能是11位纯数字', QMessageBox.Ok)
            return

        sql = "select stuID from student"
        resultSet = query(sql, ())
        for stuID in resultSet:
            if stuID[0] == id:
                QMessageBox.warning(None, '警告', '添加失败，已存在编号为'+ id +'的学生', QMessageBox.Ok)
                return

        sql = "insert into student values(%s, %s, %s, %s, %s, %s, %s)"
        execute(sql, (id, name, class_, age, gender, phone, address))
        info = StudentInfo(id, name, class_, gender, age, address, phone)
        self.student_info_list.append(info)
        self.show_student_info_list()

        sql = "update `class` set stuNum=stuNum+1 where clsName=%s"
        execute(sql, (class_))

    def on_button_delete_clicked(self):
        rowNum = self.table_widget.currentRow()
        if rowNum < 0:
            QMessageBox.warning(None, '警告', '你没有选中任何行', QMessageBox.Ok)
            return
        sql = "update `class` set stuNum=stuNum-1 where clsName=%s"
        execute(sql, (self.student_info_list[rowNum].class_))
        sql = "delete from student where stuID=%s"
        execute(sql, (self.student_info_list[rowNum].id))
        del self.student_info_list[rowNum]
        self.show_student_info_list()

    def on_button_update_clicked(self):
        id = self.edit_id.text()
        try:
            if id == '': raise ValueError()
            int(id)
        except:
            QMessageBox.warning(None, '警告', '学生编号不能为空，且只能是纯数字', QMessageBox.Ok)
            return

        info = None
        for row in self.student_info_list:
            if row.id == id:
                info = row
                break
        if info is None:
            QMessageBox.warning(None, '警告', '修改失败，不存在编号为'+ id +'的学生', QMessageBox.Ok)
            return

        name = self.edit_name.text()
        if name == '':
            name = info.name

        class_ = self.edit_class.text()
        if class_ == '':
            class_ = info.class_
        elif not check_class_is_exist(class_):
            QMessageBox.warning(None, '警告', class_+'不存在', QMessageBox.Ok)
            return

        gender = self.edit_gender.text()
        if gender != '男' and gender != '女':
            if gender == '':
                gender = info.gender
            else:
                QMessageBox.warning(None, '警告', '性别不正确，只能为\'男\'或\'女\'', QMessageBox.Ok)
                return

        age0 = self.edit_age.text()
        if age0 == '':
            age = info.age
        else:
            try:
                age = int(age0)
                if age < 0: raise ValueError()
            except:
                QMessageBox.warning(None, '警告', '年龄不正确，应为大于等于0的整数', QMessageBox.Ok)
                return

        address = self.edit_address.text()
        if address == '':
            address = info.address

        phone = self.edit_phone.text()
        if phone == '':
            phone = info.phone
        else:
            try:
                int(phone)
                if len(phone) != 11: raise ValueError()
            except:
                QMessageBox.warning(None, '警告', '电话号码只能是11位纯数字', QMessageBox.Ok)
                return

        sql = "update student set stuID=%s, stuName=%s, class=%s, age=%s, gender=%s, phone=%s, address=%s where stuID=%s"
        execute(sql, (id, name, class_, age, gender, phone, address, info.id))
        self.student_info_list[self.orderNum] = StudentInfo(id, name, class_, gender, age, address, phone)
        self.show_student_info_list()
        QMessageBox.about(self, '修改通知', '你已成功修改' + id + '号学生的信息')