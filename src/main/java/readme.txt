

Lucene测试案例

1：使用createIndex.jar创建全文索引  
	(java -jar  /data/lucene/CreateIndex.jar /data/lucene/data.txt /data/lucene/index )
	(java -jar  /data/lucene/CreateIndex.jar /data/lucene/data.txt /data/lucene/index GBK)
	参数说明:java -jar 数据目录 索引目录 编码
	回车后输入y确认，用于避免部分文件乱码现象
2：运行web项目即可进行全文查询
	在conf.properties配置索引目录(第一步的索引目录)
	启动tomcat即可