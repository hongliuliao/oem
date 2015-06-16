oem
===

This is a tool for excel mapping to pojo

很早之前写的一个将excel 转换成对象的工具包,转换成maven了

使用说明(现在只支持excel 2007):
	
	ExcelToObjectByPoi reader=new ExcelToObjectByPoi(ExcelToObjectTest.class.getResource("/"));  

	@Test
	public void testReadExcel() throws InvalidFormatException, IOException {
		File excelFile = new File(ExcelToObjectTest.class.getResource("/test.xlsx").getFile());
		Workbook workbook = WorkbookFactory.create(excelFile);
		List<User> users = reader.readSheet(workbook.getSheetAt(0), User.class);
		System.out.println(users);
	}
	
	默认使用pojo的顺序进行解析,如果有特殊要求,可以这样配置
	
```xml
<?xml version="1.0" encoding="UTF-8"?>
<oem>
        <class name="org.github.oem.pojo.User">
                <property name="id" type="Integer" colIndex="0"></property>
                <property name="name" type="String" colIndex="2"></property>
        </class>
</oem>
```
