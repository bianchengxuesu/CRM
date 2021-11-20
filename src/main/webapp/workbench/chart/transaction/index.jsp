<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

	/*
		需求:
			根据交易表中的不同的阶段的数量进行一个统计，最终形成一个漏斗图（倒三角)
			将统计出来的阶段的数量比较多的，往上面排列
			将统计出来的阶段的数量比较少的，往下面排列
		例如:
			01资质审查 10条
			02需求分析 85条
			03价值建议 3条
			07成交    100条



		sqL:

			resultType="map"

			按照阶段进行分组
			select

			stage,count(*)

			from tbl_tran

			group by stage


	 */
%>
<!DOCTYPE html>
<html>
<head>
	<title>测试标题</title>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script  src="ECharts/echarts.min.js"></script>


<script type="text/javascript">

	$(function () {

		//页面加载完毕，绘制统计图表
		getCharts();

	});

	function getCharts() {

		$.ajax({
			url : "workbench/transaction/getCharts.do",
			type : "get",
			dataType : "json",
			success : function (data) {

				/*
					date: {"total" : 100, "dataList" : [{value: 60, name: '01资质查询'},{value: 40, name: '02需求分析'},{value: 20, name: '03价值建议'}]}
				 */

				var myChart = echarts.init(document.getElementById('main'));

				// 指定图表的配置项和数据
				option = {
					title: {
						text: '交易漏斗图',
						subtext: '统计交易数量的漏斗图'
					},
					series: [
						{
							name:'交易漏斗图',
							type:'funnel',
							left: '10%',
							top: 60,
							//x2: 80,
							bottom: 60,
							width: '80%',
							// height: {totalHeight} - y - y2,
							min: 0,
							max: data.total,
							minSize: '0%',
							maxSize: '100%',
							sort: 'descending',
							gap: 2,
							label: {
								normal: {
									show: true,
									position: 'outside'
								}
							},
							labelLine: {
								normal: {
									length: 10,
									lineStyle: {
										width: 1,
										type: 'solid'
									}
								}
							},
							itemStyle: {
								normal: {
									borderColor: '#fff',
									borderWidth: 1
								}
							},
							data: data.dataList/*[
								{value: 60, name: '01资质查询'},
								{value: 40, name: '02需求分析'},
								{value: 20, name: '03价值建议'},
								{value: 80, name: '04谈判等等'},
								{value: 100, name: '07成交	'}
							]*/
						}
					]
				};

				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);

			}
		})


	}

	
</script>

</head>
<body>

<div id="main" style="width: 600px;height: 600px;"></div>

</body>
</html>