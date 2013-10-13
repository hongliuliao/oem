oem
===

This is a tool for excel mapping to pojo

很早之前写的一个将excel 转换成对象的工具包,转换成maven了

使用说明(现在只支持excel 2007):
	
	ExcelToObject reader=new ExcelToObjectByPoi();  

	@Test
	public void testReadExcel() {
		String filePath = new File(ExcelToObjectTest.class.getResource("/test.xlsx").getFile()).getPath();
		List<User>[] users = reader.readExcel(filePath, new Class[] {User.class});
		User user = users[0].get(0);
		System.out.println(user);
	}