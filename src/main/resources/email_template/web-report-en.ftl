<!DOCTYPE html>
<html lang="en">
<head>
    <!-- <meta charset="UTF-8"> -->
    <title></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/element-ui/lib/theme-chalk/index.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/v-charts/lib/style.min.css">
</head>
<body>
<div>
    <div id="app">
        <el-card class="box-card " shadow="never">
            <div slot="header">
                <div class="title">
                    <strong>Test Report</strong>
                </div>
            </div>
            <el-row :gutter="20">
                <el-col :span="8">
                    <div style="width: 100%; margin-bottom:10px;">
                        <strong>Basic Information</strong>
                    </div>
                    <el-table
                            :data="tableData"
                            style="width: 100%;margin-bottom: 20px;"
                            size="mini"
                            :show-header="false"
                            row-key="id"
                            default-expand-all
                            stripe
                            border
                            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                    >
                        <el-table-column align="center" width="140">
                            <template slot-scope="scope">
                                <strong>{{ scope.row.name+'：' }}</strong>
                            </template>
                        </el-table-column>
                        <el-table-column align="center">
                            <template slot-scope="scope">{{ scope.row.value }}</template>
                        </el-table-column>
                    </el-table>
                </el-col>
                <el-col :span="16">
                    <el-row>
                        <el-col :span="10">
                            <!-- <case-suitev-chars ref="caseSuite" /> -->
                            <div>
                                <div class="case-title">
                                    <strong>Test Suite Data</strong>
                                </div>
                                <ve-ring
                                        height="220px"
                                        :data="caseSuitChartData"
                                        :settings="chartSettings"
                                        :extend="chartExtend"
                                />
                            </div>
                        </el-col>
                        <el-col :span="10">
                            <!-- <casev-chars ref="cases" /> -->
                            <div>
                                <div class="case-title">
                                    <strong>Test Case Data</strong>
                                </div>
                                <ve-ring
                                        height="220px"
                                        :data="chartData"
                                        :settings="chartSettings"
                                        :extend="chartExtend"
                                />
                            </div>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <div class="box-card card-my" shadow="never">
                        <div slot="header">
                            <el-form
                                    :inline="true"
                                    :model="serchData"
                                    class="demo-form-inline"
                                    label-width="90px"
                                    label-position="right"
                                    size="mini"
                            >
                                <div style="margin-bottom:10px;">
                                    <el-radio-group v-model="serchData.status" size="small">
                                        <el-radio-button label="" @click.native="clickRadio($event, -1)">{{'All（' +
                                            dataList.length+'）'}}
                                        </el-radio-button>
                                        <el-radio-button :label="0" @click.native="clickRadio($event, 0)">{{'Pass（' +
                                            dataList.filter(item => item.status === 0).length+'）'}}
                                        </el-radio-button>
                                        <el-radio-button :label="1" @click.native="clickRadio($event, 1)">{{'Fail（' +
                                            dataList.filter(item => item.status === 1).length+'）'}}
                                        </el-radio-button>
                                        <el-radio-button :label="2" @click.native="clickRadio($event, 2)">{{'Skip（' +
                                            dataList.filter(item => item.status === 2).length+'）'}}
                                        </el-radio-button>
                                    </el-radio-group>
                                </div>
                            </el-form>
                        </div>
                        <el-table
                                :data="dataList.filter(item => serchData.status === '' || item.status === serchData.status)"
                                border stripe size="mini" style="width: auto">
                            <el-table-column align="center" width="50" label="ID">
                                <template slot-scope="scope">{{ scope.$index + 1 }}</template>
                            </el-table-column>
                            <el-table-column align="center" label="Test Suite Name">
                                <template slot-scope="scope">{{ scope.row.suiteName }}</template>
                            </el-table-column>
                            <el-table-column align="center" label="Test Case Name">
                                <template slot-scope="scope">{{ scope.row.caseName }}</template>
                            </el-table-column>
                            <el-table-column width="100" align="center" label="Status">
                                <template slot-scope="scope">
                                    <el-tag v-if="scope.row.status === 0" size="small">{{ "Pass" }}</el-tag>
                                    <el-tag v-if="scope.row.status === 1" size="small" type="danger">{{ "Fail" }}
                                    </el-tag>
                                    <el-tag v-if="scope.row.status === 2" size="small" type="info">{{ "Skip" }}</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column width="140" align="center" label="Start Time">
                                <template slot-scope="scope">{{ scope.row.createTime }}</template>
                            </el-table-column>
                            <el-table-column width="140" align="center" label="Elapsed Time">
                                <template slot-scope="scope">{{ getDifftime(scope.row.createTime,scope.row.endTime )
                                    }}
                                </template>
                            </el-table-column>
                            <el-table-column width="180" align="center" label="Operate">
                                <template slot-scope="scope">
                                    <el-button
                                            type="primary"
                                            size="mini"
                                            @click="viewDetail(scope.row)"
                                    >View Details
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <case-detail v-if="caseDetailVisible" ref="caseDetail"/>
                    </div>
                </el-col>
            </el-row>
        </el-card>
    </div>
</div>
</body>
<!-- import Vue before Element -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<!-- import JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/element-ui/lib/index.js"></script>
<script src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/v-charts/lib/index.min.js"></script>
<script>
    Vue.component('case-detail', {
        // 步骤1、父组件通过props向下传递数据数组list给子组件，子组件需要通过props声明注册‘prop’;（注册的prop可以和数据不同名）
        template: `
        <el-drawer
        title="Case Details"
        size="50%"
        :visible.sync="visible"
        >
        <div style="margin:0px 20px 0px 20px;overflow:auto;height:calc(100% - 15px);">
          <el-table  :data="dataList" border stripe size="small" style="width: auto">
            <el-table-column type="index" width="40" align="center" label="ID" />
            <el-table-column align="center" width="180" label="Step Name">
                <template slot-scope="scope">{{ scope.row.stepName }}</template>
            </el-table-column>
            <el-table-column align="center" show-overflow-tooltip label="Actual Result">
                <template slot-scope="scope">{{ scope.row.logDetail }}</template>
            </el-table-column>
            <el-table-column width="70" align="center" label="Status">
                <template slot-scope="scope">
                <el-tag v-if="scope.row.status === 0" size="small">{{ "Pass" }}</el-tag>
                <el-tag v-if="scope.row.status === 1" size="small" type="danger">{{ "Fail" }}</el-tag>
                <el-tag v-if="scope.row.status === 2" size="small" type="info">{{ "Skip" }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column width="120" align="center" label="Screenshot">
              <template slot-scope="scope">
                <el-image
                  v-if="scope.row.imgname !== ''"
                  style="width: 80px; height: 30px"
                  :src="'data:image/jpg;base64,'+scope.row.imgname"
                  :preview-src-list="['data:image/jpg;base64,'+scope.row.imgname]">
                </el-image>
              </template>
            </el-table-column>
          </el-table>
        </div>
        </el-drawer>
        `,
        data() {
            return {
                visible: false,
                dataList: [],
                addOrEditFormVisible: false, // 新增或者删除弹出框默认值
                srcList: [
                    'data:image/jpg;base64,aaa'
                ]
            }
        },
        methods: {
            init(row) {
                this.visible = true
                this.dataList = row.testStepLogs || []
            }
        }
    })
    new Vue({
        el: '#app',
        data() {
            this.chartSettings = {
                radius: [40, 58],
                offsetY: 400,
                label: {
                    formatter: params => {
                        return
                        ${r"`${params.data.name}:${params.data.value}`"}
                    }
                }
            }
            this.chartExtend = {
                legend: {show: false}, // 隐藏legend
                series: {
                    center: ['50%', '50%']
                },
                color: ['#1890ff', '#ff4949', '#909399'],
                title: {}
            }
            return {
                tableData: [{
                    name: 'Task Name',
                    value: '-'
                }, {
                    name: 'Start Time',
                    value: '-'
                }, {
                    name: 'Elapsed Time',
                    value: '-'
                }, {
                    name: 'Test Suite Sum',
                    value: '-'
                }, {
                    name: 'Test Case Sum',
                    value: '-'
                }, {
                    name: 'Case Pass Rate',
                    value: '-'
                }],
                chartData: {
                    columns: ['result', 'sum'],
                    rows: [
                        {'result': 'Pass', 'sum': 0},
                        {'result': 'Fail', 'sum': 0},
                        {'result': 'Skip', 'sum': 0}
                    ]
                },
                caseSuitChartData: {
                    columns: ['result', 'sum'],
                    rows: [
                        {'result': 'Pass', 'sum': 0},
                        {'result': 'Fail', 'sum': 0}
                    ]
                },
                serchData: {status: 0},
                dataList: [
                    <#if dataList?? && (dataList?size > 0)>
                    <#list dataList as testcase>
                    <#if testcase_has_next>
                    {
                        caseName: "${testcase.caseName}",
                        createTime: "${testcase.createTime  ? string('yyyy-MM-dd hh:mm:ss')}",
                        endTime: "${testcase.endTime  ? string('yyyy-MM-dd hh:mm:ss')}",
                        status: ${testcase.status},
                        suiteName: "${testcase.suiteName}",
                        testStepLogs: [
                            <#if testcase.testStepLogs?? && (testcase.testStepLogs?size > 0)>
                            <#list testcase.testStepLogs as stepLog>
                            <#if stepLog_has_next>
                            {
                                imgname: "${stepLog.imgname}",
                                logDetail: "${stepLog.logDetail}",
                                status: ${stepLog.status},
                                stepName: "${stepLog.stepName}"
                            },
                            <#else>
                            {
                                imgname: "${stepLog.imgname}",
                                logDetail: "${stepLog.logDetail}",
                                status: ${stepLog.status},
                                stepName: "${stepLog.stepName}"
                            }
                            </#if>
                            </#list>
                            </#if>
                        ]
                    },
                    <#else>
                    {
                        caseName: "${testcase.caseName}",
                        createTime: "${testcase.createTime  ? string('yyyy-MM-dd hh:mm:ss')}",
                        endTime: "${testcase.endTime  ? string('yyyy-MM-dd hh:mm:ss')}",
                        status: ${testcase.status},
                        suiteName: "${testcase.suiteName}",
                        testStepLogs: [
                            <#if testcase.testStepLogs?? && (testcase.testStepLogs?size > 0)>
                            <#list testcase.testStepLogs as stepLog>
                            <#if stepLog_has_next>
                            {
                                imgname: "${stepLog.imgname}",
                                logDetail: "${stepLog.logDetail}",
                                status: ${stepLog.status},
                                stepName: "${stepLog.stepName}"
                            },
                            <#else>
                            {
                                imgname: "${stepLog.imgname}",
                                logDetail: "${stepLog.logDetail}",
                                status: ${stepLog.status},
                                stepName: "${stepLog.stepName}"
                            }
                            </#if>
                            </#list>
                            </#if>
                        ]
                    }
                    </#if>
                    </#list>
                    </#if>
                ],
                caseDetailVisible: false,// 新增或者删除弹出框默认值
                planLog: {},
                suiteLog: {}
            }
        },
        mounted() {
            this.tableData[0].value = '${planLog.name}'
            this.tableData[1].value = '${planLog.createTime ? string('yyyy-MM-dd hh:mm:ss')}'
            this.tableData[2].value = this.getDifftime('${planLog.createTime ? string('yyyy-MM-dd hh:mm:ss')}', '${planLog.endTime ? string('yyyy-MM-dd hh:mm:ss')}')
            this.tableData[3].value = ${planLog.suiteTotalCount}
                this.caseSuitChartData.rows[0].sum = ${planLog.suiteSuccCount}
                    this.caseSuitChartData.rows[1].sum = ${planLog.suiteFailCount}

                        this.tableData[4].value = ${suiteLog.caseTotalCount}
                            this.tableData[5].value = this.getRate(${suiteLog.caseSuccCount}, ${suiteLog.caseTotalCount})
            this.chartData.rows[0].sum = ${suiteLog.caseSuccCount}
                this.chartData.rows[1].sum = ${suiteLog.caseFailCount}
                    this.chartData.rows[2].sum =
            ${suiteLog.caseSkipCount}
        },
        methods: {
            clickRadio(e, index) {
                if (e.target.tagName === 'INPUT') return // 因为原生click事件会执行两次，第一次在label标签上，第二次在input标签上，故此处理
                if (index === -1) {
                    this.serchData.status = ''
                } else {
                    this.serchData.status = index
                }
            },
            viewDetail(row) {
                this.caseDetailVisible = true
                this.$nextTick(() => {
                    this.$refs.caseDetail.init(row)
                })
            },
            getRate(num, total) {
                num = parseFloat(num)
                total = parseFloat(total)
                if (isNaN(num) || isNaN(total)) {
                    return '-'
                }
                return total <= 0 ? '0%' : (Math.round(num / total * 10000) / 100.00) + '%'
            },
            getDifftime(startTime, endTime) {
                if (!startTime || !endTime) {
                    return '0 s'
                }
                var d = Date.parse(startTime)
                var d1 = Date.parse(endTime)
                var date3 = d1 - d // 时间差的毫秒数
                // var date3 = date2.getTime() - date1.getTime() // 时间差的毫秒数
                // 计算出相差天数
                var days = Math.floor(date3 / (24 * 3600 * 1000))
                // 计算出小时数
                var leave1 = date3 % (24 * 3600 * 1000) // 计算天数后剩余的毫秒数
                var hours = Math.floor(leave1 / (3600 * 1000))
                // 计算相差分钟数
                var leave2 = leave1 % (3600 * 1000) // 计算小时数后剩余的毫秒数
                var minutes = Math.floor(leave2 / (60 * 1000))
                // 计算相差秒数
                var leave3 = leave2 % (60 * 1000) // 计算分钟数后剩余的毫秒数
                var seconds = Math.floor(leave3 / 1000)
                // // 计算相差毫秒数
                var leave4 = leave3 % (1000) // 计算分钟数后剩余的毫秒数
                var ms = Math.floor(leave4)
                // var ms = Math.round(leave4)
                if (days + '' === '0' & hours + '' === '0' & minutes + '' === '0' & seconds + '' === '0') {
                    return (ms + ' ms')
                } else if (days + '' === '0' & hours + '' === '0' & minutes + '' === '0') {
                    return (seconds + ' s' + ms + ' ms')
                } else if (days + '' === '0' & hours + '' === '0') {
                    return (minutes + ' m ' + seconds + ' s' + ms + ' ms')
                } else if (days + '' === '0') {
                    return (hours + ' h ' + minutes + ' m ' + seconds + ' s' + ms + ' ms')
                } else {
                    return (days + ' d ' + hours + ' h ' + minutes + ' m ' + seconds + ' s' + ms + ' ms')
                }
            }
        }
    })
</script>
<style>
    body {
        margin: 0px;
    }

    .title {
        width: 100%;
        text-align: center;
        font-size: 21px;
    }

    .case-title {
        text-align: center;
        font-size: 17px;
    }
</style>
</html>
