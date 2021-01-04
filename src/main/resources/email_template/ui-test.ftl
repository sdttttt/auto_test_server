<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
            color: #6a6a6a;
        }

        a {
            font-size: 13px
        }
    </style>
</head>

<body>
    <table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td height="10">&nbsp;</td>
        </tr>
        <tr>
            <td valign="top" style="border-left:1px solid #CCC; border-right:1px solid #CCC;border-top:1px solid #CCC;">
                <table width="620" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td height="92" style="background-color:rgba(51,204,255,0.6); padding-left: 20px;">
                            <a href="http://${params.webip}:${params.webport}${params.contextpath}" title="LuckyFrame" target="_blank"><img
                                    src="http://${params.webip}:${params.webport}${params.contextpath}/static/favicon.ico" alt="rabbitTest" width="70"
                                    height="70" border="0" /></a></td>
                    </tr>
                    <tr>
                        <td height="1" colspan="2">
                            <hr style="border-bottom:5px solid #f1f1f1; display:block;" />
                        </td>
                    </tr>
                    <tr>
                        <td height="20" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="40" colspan="2" style="font-size:12px; text-indent:25px;">
                            <div style="margin: 0px auto; padding: 0px 10px; width: 680px;">
                                <div
                                    style="color: rgb(77, 77, 77); line-height: 1.5; font-size: 14px; margin-bottom: 25px;">
                                    <strong style="margin-bottom: 15px; display: block;">亲爱的Tester：
                                        您好！以下是自动化任务【${params.jobname}】执行情况。</strong>
                                    <br>
                                    <p><b>本次任务执行业务流共【<font color='#2828FF'>${params.businesscount}</font>】条,耗時【${params.businesstime}】秒</b></p>
                                    <p><b>本次任务执行用例共【<font color='#2828FF'>${params.casecount}</font>】条</b></p>
                                    <p><b>用例执行成功： </b> 【<font color='#28FF28'>${params.casesuc}</font>】条</p>
                                    <p><b>用例执行失败： </b> 【<font color='#FF0000'>${params.casefail}</font>】条</p>
                                    <p><b>用例执行跳过： </b> 【<font color='#FF0000'>${params.caseskip}</font>】条</p>
                                    <p>&nbsp;</p>
                                    <p> 此为自动化平台RabbitTest的系统邮件，请勿回复</p>
                                    <p> 请及时前往<a href='http://${params.webip}:${params.webport}${params.contextpath}/#/uiTest/result/detail?id=${params.planLogId}' target="_blank">RabbitTest平台</a>查看您的任务执行的更多细节</p>
                                    <p>
                                </div>
                        </td>
                    </tr>
                    <tr>
                        <td height="10" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="40" colspan="2">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>

</html>