package com.oruit.app.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2017092308880622";
	
	// 收款支付宝账号
	public static String seller_email = "619907467@qq.com";
	// 商户的私钥
	public static String key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCRNXQ12tOk7WrZIGEQ+XFJ9jgtBV6wVEITnaZ/mWYLpj9AOy/sw6bKMF25mKbVFjgwEtHQJwcxwSIjtxS1ausnFx6+gf/50iPqTT5viBEv73K984ER7Aodewn4XLQnXa4iru0fSJ1On0+ZiKAlLiDFm4f1XespWmXNPt0p2fqrJEgaIcqWBh+hXPbzPyYtWxQvHe5OPdorF227sNncLE+pd6NPSdVZXJZW3ZAr5HOfxbqkNOZImHVIHb+lnX8lUgIta48GNEKGDykANNG5NJnjFrPY3F2gYnHjaSd82TWOF5faOZM/oa1usHOoB1lxjVtuPtAzKUrnH7yH5VRXFc3FAgMBAAECggEAZTUqkDa6+OL5a+hjfx881N4l/UsW2jN/8ogY+iqCs2/l+fDG0HD1mUroqGk7Lp3T/72+OyRYoVcJYndCmcexqoc0yHd3YDoVCkNYVwgxYoW0JazIsyMfUbjmFBMZBa8HpejiIMHN0nS7WDmf1Qo3kUN8GYqiUypMdidworDpQ8pvppy1T9IYKABCDZ31oqWqPcPU/Tiak/FiHN70UlhTKTSKqp7n6xAMCu5Ifyb5gz21UiXtdPJGgPAIesQGNJbHVYqgID5iGz7ChjtSn1/Kcp6yF02u13g4+nqVY6W0SpGliS4KmdAyqXlQ4UBbXH8zK2r+CvKmuldExHY/LMkY6QKBgQDinZCOpHcrLGBiJL/F6ig5cNOS2uk31qWHzh5xl6j7Vr2/jZp2wZZ6aKywUjGgixrAWoqjDJ+KrSgCzvjykhrwo7nZehapPZbpS3l0o0OQfg/GkcvRWjoyMuFS6JDJDAR+DHMsVp+2NkumgpGhc7JBgfisFpT3Z0+MqQ4FFbGyAwKBgQCkCZ45xv2H4xbdp2l9PswnUIKWibSyCqsNUI07ujbJXnxl/HpkqxNmQynquebf+yCOTVLKZb/YA6AmV+PF57amCvG2vecWgs6jGdZFedJb48sUHc5TMR+U8MVvaup7gQsyq/XLYFgl87eLH2wqUU2Oo0IhnMWL10D8tA/tghCalwKBgHHeoLu8UpEkHE5UjvSBI7MxSW7ahyPivIyP+uYzQdsBjBkLq1kPD3D01glHU96K52dOV6l0d1p0hc1beOZSmize6E0qNubVptYVcbM1MocRgRXTPuo0f1eoD2bcq5wKaNt5oAnggSJLCBpkM49AFosqJu0576Kdi/BvJjdR3ldHAoGAHxgvpRYiYwrZ/8RHS9WGj4FEb9LErOc2y3xUeVjwH7XIcPCuJr0DHWRn4rXAnP6WikMACZvGBOWTCxGn/JDS36C9dv2Ce5LqJqK0KlFQXdjNWw/XV4+9Q9dsJZWnwyeP/tf37MhjjTYoubeIInFs+TfbRlHYZIy2JfDgAA6ipRsCgYBLQanRcDGuPvsqjTcLenDUvzYSkXn8W3kOemJ71hqUEmpQxipoSQ1WDZ5Xru30Ogi48CjajR2SOgVTucRm2kauYVinsDcjcaS3cOWgvjRz4wzl7O4m9KIlpWnBS9aWxNsc0B08EOVFB5naM0AyLNjzUFElOM0OzxyJXWWrI9IzGw==";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";

	// 商户appid
	public static String APPID = "2017092308880622";
	// 私钥 pkcs8格式的
	//public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCc8RQFLjjQnYoqIHFedhQnC6jMRA/XOzfu6CJRFc79BzX4JvqZ4EuzScObAeRrl//qyCLBIQ0R2HdjFX8kopyNBvzXcjLC4JDsda0dMkVT0KxoRV2ticch9GT4/7ntXi5QIOpLsNLjCNMTu/6iIZBfe4v9/FAbBwWCMDPk1L1yYlyQdXFOl8oToz2Tr0mcQYldWZkcBXI3s79BDlBYhOl1RGzUqrS4HZnEYSWl3LbW3VazMpY185ShpbaBfBS6dDr9wiSiSQzIelEdlorPIXtAUdwSg2i1ShpHllpLc1Q7SJKXGqQxzsEOfC33p5GBsGSsc3iEJh2cK4YBMqQrgBplAgMBAAECggEBAI00w8Sdf/UDqgqZQiexqhQvg2K2YmJPH9ZO+oFmU+SJll9+AsBsdPANkHdpTnIYQuSlyc9mw3GpCFIYR1tZC+4/uULHtzBsCTkX7/hzALmW6Wiv8MdxTcAZOrhmro0JzjW+R9BuenBXwkLhRQvvkwy2BpExcYm/2Y73e9mKKCI/QP0qzG5t6RzJkfJFPKEFdby8sXC8NaOm5+wbGSy/WYeCQ5ZIy8966krEzmaHsEVYQlQcs9QVVrT4Mb6wuefrbXMGj0TBI7UlsCUT/7qorAWQx5nplc+nQTGNdXRf8qn0+xlIBImjuwXsRz/3wSW6cWZ44IN6Ia0kUgeI7G7OsEECgYEA0GwRww1xsxgSBYg9N5/AHupNTlhcL4LBXvmK/X0ArVxtN96+AbFkUa2UV8AYWw2xMiSTm4YU4jPN9eSxTIuCiDgcTNb0I0ul1dHbYJs0QuOIvU5jyEkG86J2HnuvbO9gqEPt+vRAcKZYpgOecc6WlLKaKFMgL+4cBXIQKqSPqF0CgYEAwMSObb5NusIBQImPgqNZMWj9w8FaX8lkjxoqhHJUKiVGizk7uzaOGihTf4Ts7AFmqt0aFi67UbMPO8STUARW9jyc97l6TIY6RDdnVgFsvD0FFGeBflzn/BqIwT5WNzlBpjSzeUKFewqrbou7QAdPX2xgRLPP2sWFnsbHmVEOeakCgYAg26jBM3FlZroQnTXpewny1cVyQIPoCZkqu/SpmfdtXVDqqfcSlUDXj6H/Y41zbWkuGo4ayryoHLqvtWouQyBFEXOEua6rkdWHLBqF1HIcyXpZPe6FON4bstUkYSN67RIz9OGj2rX+DHuVUKS+FG+w6adcDB7a75MDWMQrtN3hEQKBgBjfQ6FVVKIsNg0lsqagfPmrGlMPt6F0Us1ECwhb2BA4DwoXsGVPfLZTTbK+a/Nnmt+g96S1EuZPvD9x9a/1X2MWHwq3m9gT2aDDpUmrlWQUW3tuf37WbfhRVCPa7zTg5l+TnTSgKKjfj48L6HUyfENLsIAs0zzfD4aVD1vw4XVBAoGARasqWmn4fjFdVckSyJ+Z5ynRmC5ttwLamuYpUXBCJ3ev+p3+OQk7tOt+qnzPToSGyKgniByM5CqD1+W1rwuCVeqIDaDVtqb+n0+8XuLjushdmpRui3Pw+W1Ro4SmTtZ4P0REqFL5DKI71mWEM9kNJw68N0JKIruHXaD/M5S/ak0=";
	//public static String RSA_PRIVATE_KEY ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCz3aYmGA2wnJ5Emq2Fo9tKb2E8Oq8l7DfTflQFySR7Fg3fnpXuE4UrNrWyDSYBOusPVmYy3ote/OGm8ifb84LeCO2dTPHD9U1vQmnA866iiNUA7gGkSwON0YO+B3IRG3A/jlLMd05EwbysFV0XHKKMWJsGDCD46991FPhQsz5lp43b5xQTpNAMJGN1rBh0jLG5gzTPDFqFoaQ9rot5iYSQg2Pngvh7aDrIOuMBzzcXx4uB5OZKRD9wKo8MwnHDlvi1DBCwlfWvOhBisszdtUVNB+9aPhaBsO50GadGHzd2/ZFT+K+9rsbFegX0XE9hjdeEH20rJ+gwYI6xHOIzCNDLAgMBAAECggEBAIXRl4bqCG1o6NuT1RwDn+iOW2KLKrplN9ezOmIfl3O4IdRI0bWSclaHWsFAxRDPPAlqN5kLN9gQ/vXNXlhnoOr+64JHt+waOmVXnQTAiCJG+Wq6qSAJ63f89C5SrcwKhQXUJsgWz3WaSnwhXOQQGuOeHKBwL6vjGZqrYl/wK7FGqzCQU6H2/axvK6oImxztPvrdtvmKL3kv5EPz/j/xD4PDJNioUyNlRoARQPUzfa/7cj3Im2xZYbAiNrmYRtPGwFOoR6/DDCye2zBtmBlpfNgRcq8VkgRQTwCTfUXM5lm8wFmr33bVXkyfXvuHkQ+E61jhB6ji2Y6xPHVGLNclH5ECgYEA4a//YWjIp3dpmP1ZcmvHkpY9QyaKWw51vkErbNOVi+7g+jjIIRWwlUH4yvSs/lmun1WDqNYeIQBcqxwlAK4dKjciepXEodvv6Bp2WayX+IIadK0tKhRt9WVVWuUFfNWcXa8up1LplsFRfco+T6WhWEOqrIYU057xWazyfSqrF/kCgYEAzAYgWQb9PMV9eNOadioTe6iAYtPPwUYK2bz7Zz/61LsNkDHS8lfpgQx5JLJpQkuv5OUtB6S7J3GNmd6XNnAHuNZghRaZC19uTS9kjekhLGEsoEKes9Q7+lGAPUXK79FmBWQ4PHm4AjJiQYL3ILdRxAw1Tg6j0Doo13P5Epcwx+MCgYAQf6REw7s/etxOYd8DHVv2nGMYIJ4mr/TtlqgNCZ66Hxl1UWdX+9Tv5GXCuIZZBcddQIwicJy9PuCHZcRSu2fIaUVs/rvkNUbhieWzAooNkb1LYEmX9Oxg7yqBPZLcIGWQ97iKZOZMrg8+CeXqPqp/4Fp+yEKHgewU6S7E9bSSSQKBgQCL/LwfObtyAjOYPuBhqXcaxr/oj1qiS1PQmGmLK4wIgJvKz1Tk8UsHP66zbjLoyRPUIyclcnLzmmdFJ0RrlqzFt1oclyfFdOIy1YzSDh6z260J/Qv1aW8JzvgFkqiuqeetVVl8rSuDXlyqhJjZ+s6AjLvaZhMiIXW6dxl0FwbibwKBgQDB3MClSVkSkkh2VpUNPjRQ5DVzUIXVKkct4eB6sbG3Qv0OkX4Ea6G3vi1mV/3hKS2nXPMiFUC+5BZAz7/8UHtBYznrDnepPXoxsBOyvooJ1DhqDIkfwwCRuZBzRDdJ6KFE9mEd4bK8a2sO2NmXZARSnLG3Q3RS0tj1WPmWoVYtLg==";
	//public static String RSA_PRIVATE_KEY ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDIl9aaS4syQ340fUGRg8HvpoBW20t4l04I37pzHW+r2/KFjSsxzvraFzJDQjyAbL8DxgG6RCcgbBSfBPkdbbo49iLjsY4Cx52dx8myaae53HHgUHHqYZAHddo4jywuE/XFRoesDN/lbiPdkNyqIenns97jLFSahSa8JqNw56Ak4SYrQz48IM9LojIrmn8zqNmxxCkouNi2pveLQLOHbbJZa5OljoYWiBh7GEjNnzPS7tpdgWAiCKDO6pbYpo+Zw792juEhIuzJzrG/MRC0FwoExAoZuU6lfGFfRtrMiyCh/aDbZwSFj87n6v8OTl8zHlZJ8fTimLjj+IzKiBnujxJlAgMBAAECggEAT7h4o62uTnWgbL/zFR/mLZZgnyRcZDrN2pz//swOG6E4S3eFWTBHZfGeGjjyzp9pcth4y0xWuH373A8f6mA3iHfZ+eAQ7m3i6FgEgxGWhTllUJ7oBxxrH2vpJF1SU6W/IX4cYXZkbUxOmLn/2OXNjjZgOKzfmuGcOV6pyi9j6AOWcqiV1qZ9oWzuc95p3eFI6DBJmqwogb7jLDlPkZ6M1/RortcpEgAN/wjlgGVx+GjM+vGIY9FxHYXQdtAyRexuAESPGcdJRqntfQ0bbZJaT2+nbV59T4Twen3tt+7NH94WqAhbq2/6vJ7ATlMcBHienYhIojqQsU7EbUJ53/hhwQKBgQD1fEOyyasvcqWWrtHsDjaRBxHc9ODzUYNNckJ7FpSKlMTi1kmx9D2cJWQLXlXxw9A6xgB41k3lsrL+2QvJiHlGapMwlILAiLT/8bBQjaGpC0ZEpa03qrcE3JfQaFIGqNp1w/4v3gRkIuf2uzS6d4OopC7yWLR3jB/Fjfqh8naubQKBgQDRL1UCwZPp9RJjnOU36a0ccUjlYlr7BPD6cln21aGZPwHHAqe5/2hOYmno2vX3KlFsqWMH1d1RqGLIDhDnnywLMZUfdCr75bNgJ8i7U9SKxMAgaxWO2eGmCQTxIgfyfoqFwDLfqimZeFAt4Su+pURZ9sL4Dl8gGoA+TUq9aScY2QKBgCnuEbomDCFMmdAFCEIjo1peNMTi5tFoqN6/l29iyfhptL9yDqBgsW1MTaxLbq6e3aBMVaROJnwHa99vvz2rG9vVNFLl6vdr4k6dP5pitqwjCtuK2O1bMWuZKi0kxG3yxW28DlyqtWFNPQW9sCHxbAGQGZy3XgvVWU/rElY7Dd55AoGBAJKmZSm2fjUBu6+cGdJV/ye2f81YY8IhV7OsoUd7w3vFAUD8qoup8/0n32oAv3lU8JqGMcgXdb4+l2n6+nz3SRBb5ViUv2GzSkq5+W7iuVnDz5GqoQVx2FDnd9B49Ctb7U8RTFkjWWCfSU6w0lMoV7/eUBdJNAU0rv1+gExDwMYZAoGBANpMwna1HbWdAYJL7Xwk6PBBUgg8oOXchTcYo2htb2hNe2FNObQ6rfxpm0xsTY229k0PeFibV0Hmw9kPv+tSRT9rDBxlnMI0zk9gW/78pCs87SpQ3wOVU0SoEukvefPeZwLPXEESQ6+KK6RCwFj8kInDbd1ZjiWC6XgqoO6Ouq0m";
	public static String RSA_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCs82QlPvzs29BFRFQlpcuO4+0VVmNpN3J0AJEXg2wmqDeg1KmWS63TXV1/9ZX4owL6SMNqljvSEZf53D7PidMPi04Wf3SakruWNHqjAt/zSlS/5P53jM5d905dOeH+zrIN3vVU+hqk09RAJBrqIL+mcAZmzg8SiO0UwGJ3oF45+/wnOaq5cedAbH/K5VAdNwdyRfc7X69UE7Ot0tHbxQpUz/mIQQ2qZ/xo6c/bfeJuDwlYriWFaxr+o654DEIugAqF46gaJaT7QS0PFZO1Q4Fv1IA1zqc6U2r0Q4xhrzSF9DyySQu+EECENACFtq17i6gSjhApRh3zPvffM6a88AADAgMBAAECggEADF8cus1WqlrO+yV4H3lMplwHiY80bUJaHZIsztHH2rwNvqN3vhiLxqLXHWAj6jU+8UhjMiys+snnZSgsFgx13JbKbzGO1b/JS2VVoHOEMZMPnOp0iXi5Cl+2a1TIpcGDWxqn0fxSC5hbhnsOsdDF5NH+ozv6OAkzbFiWspf2JY+Szl5oha1YXuG1PbYUrwgaD34LsH875qY+neFyEfd3TihDVFpTcjnObIYGOe1dzu90iR4A1I+6QYjbh7e2cKNZv460YFgTHO8N7H/UwIAsPDAkDp96ci+7h6qabla1+zObj9NB9ySeRZyWGCEODsKOyPr1VJVJccOKQDS0Co2MAQKBgQDV+gacnab4ePx+72BBD4rn21TVF467SoskhrCHwxaWFXiGPY5SXQRQNlHf7d8toheMsDEeEPbR+BjRh21zjdN22ry2z+Ikm/40eHuDCZGst4Dye9uzqvJjMphaTU+2sZg69e57f34EfCDVXBj3F8cYKqnMI1jB6vw3V8vbhqOrYwKBgQDO6rr6qeOA5XekRl57/T9JHQ1bAuRfLvx59VQYG6fU7FycipuWy1rmZCcTwN0xESPbbO/RJOpNU0KBKvuFpyEWkVpG93Q9siWLnbbUXaolTQ6ELtWfl492UJ+5BJVtH9l0AXEusYry9EKVf7lVarlWe+Rbo32hi3vC8g5xQnGK4QKBgGI4Yb6Uc2eNCcIumU1kGAvz1w31wVPhlNtQZEn9WCnOc0OTj/fosKktNgoHQ7HLvOmcmv9FJDcfnizX8HrheFcPcx6rcBAD9v0Y7QEqbE8O8Mbd6eygJKE6QUr6QAQJo2o1J6JS1IK0g9HMc71Akeha9RQ6iWyCNAInb/E17X0nAoGBAJ4mZRFvxlZfjQl/S3pDPxq33NxiIoDp3cmtjD/861Nf8Q0pF/Cyr2CGziNX9vgo3Jo68jWNnJPLfnpRivtFmZGxOSoiurLpNa83tWUtzf1Cn0TCsz6OzfUlfF8itL86Z6815vdEdipqLXYssFAsCUqj2Q99r2lIvihrcER6/2OhAoGAFauWiXPKJSe1fZ6nSwgQhCG1TP6zXaZmyOMiq83kfIwbbYFfiMPwErWLebZhtZASCUYgpel1fe9CPRzZZzeTcHDRMKxPTbhoEFYJ1GW4OZC62Vxs8lx6TjvG666z8gH8OOCuN+5pjnnB4fopmQLjWsNmXxca0T5sQ6/F1PzXgMM=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "https://www.cetan.com.cn/lottery/h5notify_url";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://www.tminz.com/alipay/success.html";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	//public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";
	//public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAivcNvcDAMeVPH047GuXQ5lboSOC9Ez3zXPfbTJdxk9eZyMrnWDnRq/cDKsrjaW6pNBH5WUFq9QOXvHaLt9A6MOEnxY8qhO071nz0sEhlGGeM6FqJy1MywI5n22GxFqfvaWixP73xY5TTi5TowSu4kfjJxaxzKUtSpHxFvyH8EUDDG3dAh6uBgKWOjUSSTmAnT7+OGYMzb860HU3ORdXI/3MGZa5np7KDVw8Yi5Vv8VUwRfxGqYhRzE2IuSPGX+j4OHaFhSKqaQVp5mJKhX7LCv/9/hKAW1UYc6QTouK4KJh/FzFOdNYsYoRLsqhw84JTvFT8bduWztYs1Gt0t5VcBQIDAQAB";
	//public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyJfWmkuLMkN+NH1BkYPB76aAVttLeJdOCN+6cx1vq9vyhY0rMc762hcyQ0I8gGy/A8YBukQnIGwUnwT5HW26OPYi47GOAsedncfJsmmnudxx4FBx6mGQB3XaOI8sLhP1xUaHrAzf5W4j3ZDcqiHp57Pe4yxUmoUmvCajcOegJOEmK0M+PCDPS6IyK5p/M6jZscQpKLjYtqb3i0Czh22yWWuTpY6GFogYexhIzZ8z0u7aXYFgIgigzuqW2KaPmcO/do7hISLsyc6xvzEQtBcKBMQKGblOpXxhX0bazIsgof2g22cEhY/O5+r/Dk5fMx5WSfH04pi44/iMyogZ7o8SZQIDAQAB";
	public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAivcNvcDAMeVPH047GuXQ5lboSOC9Ez3zXPfbTJdxk9eZyMrnWDnRq/cDKsrjaW6pNBH5WUFq9QOXvHaLt9A6MOEnxY8qhO071nz0sEhlGGeM6FqJy1MywI5n22GxFqfvaWixP73xY5TTi5TowSu4kfjJxaxzKUtSpHxFvyH8EUDDG3dAh6uBgKWOjUSSTmAnT7+OGYMzb860HU3ORdXI/3MGZa5np7KDVw8Yi5Vv8VUwRfxGqYhRzE2IuSPGX+j4OHaFhSKqaQVp5mJKhX7LCv/9/hKAW1UYc6QTouK4KJh/FzFOdNYsYoRLsqhw84JTvFT8bduWztYs1Gt0t5VcBQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";

}
